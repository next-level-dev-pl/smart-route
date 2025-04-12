package pl.nextleveldev.smart_route.busstop;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import pl.nextleveldev.smart_route.infrastructure.um.UmWarsawClient;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
class BusStopLineUpdater {

    private final UmWarsawClient umWarsawClient;
    private final BusStopRepository busStopRepository;
    private final BusLineRepository busLineRepository;
    private final EntityManager entityManager;

    @Transactional
    @Scheduled(cron = "${bus-stops.updater.cron}")
    public void updateBusStopLines() {
        log.info("Updating bus stop lines...");

        List<BusStop> all = busStopRepository.findAll();
        all.forEach(busStop -> {
            busLineRepository.deleteAll(busStop.getLines());
        });

        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (BusStop busStop : all) {
            executor.submit(
                    () -> {
                        try {
                            eachBusStopUpdate(busStop);
                        } catch (Exception e) {
                            log.error("Error while updating stop " + busStop.getStopId(), e);
                        }
                    });
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(30, TimeUnit.MINUTES)) {
                executor.shutdownNow();
                log.warn("Executor timed out");
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
            log.error("Executor interrupted", e);
        }
    }

    @Transactional
    public void eachBusStopUpdate(BusStop busStop) {
        log.info("Updating bus stop: {}-{}", busStop.getStopId(), busStop.getStopNr());
        try {
            var response = umWarsawClient.getBusLineFor(busStop.getStopId(), busStop.getStopNr());
            Set<Line> lines = ConcurrentHashMap.newKeySet();

            response.lines().forEach(lineId -> {
                Line existingLine = busLineRepository.findByLineIdentifier(lineId)
                        .orElseGet(() -> {
                            Line newLine = new Line();
                            newLine.setLineIdentifier(lineId);
                            return busLineRepository.save(newLine);
                        });
                lines.add(existingLine);
            });

            busStop.setLines(lines);
            busStopRepository.save(busStop);

        } catch (RestClientException e) {
            log.error("Error updating stop: {}-{}", busStop.getStopId(), busStop.getStopNr(), e);
        }
        log.info("Bus stop {} updated.", busStop.getStopId());
    }
}

