package pl.nextleveldev.smart_route.busline;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.nextleveldev.smart_route.busline.entity.BusLine;
import pl.nextleveldev.smart_route.busstop.BusStop;
import pl.nextleveldev.smart_route.busstop.BusStopRepository;
import pl.nextleveldev.smart_route.infrastructure.um.UmWarsawClient;

@Service
@RequiredArgsConstructor
@Slf4j
class BusStopLineUpdateService {

    private final UmWarsawClient umWarsawClient;
    private final BusStopRepository busStopRepository;
    private final BusLineRepository busLineRepository;

    @Scheduled(cron = "${bus-stops.updater.cron}")
    public void updateBusStopLines() {
        log.info("Updating bus stop lines...");

        List<BusStop> all = busStopRepository.findAll();

        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (BusStop busStop : all) {
            executor.submit(
                    () -> {
                        try {
                            eachBusStopUpdate(busStop);
                        } catch (Exception e) {
                            log.error("Error while updating stop {}", busStop.getStopId(), e);
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
        log.info("Updating bus stop {} and {}", busStop.getStopId(), busStop.getStopNr());

        var response = umWarsawClient.getBusLineFor(busStop.getStopId(), busStop.getStopNr());

        response.lines()
                .forEach(
                        line -> {
                            var busLine = new BusLine();
                            busLine.setLineIdentifier(line);
                            busLine.setStop(busStop);
                            busLineRepository.save(busLine);
                        });

        log.info("Bus stop {} updated.", busStop.getStopId());
    }
}
