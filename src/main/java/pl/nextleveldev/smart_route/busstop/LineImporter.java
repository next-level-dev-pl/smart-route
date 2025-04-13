package pl.nextleveldev.smart_route.busstop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.nextleveldev.smart_route.busstop.joinTable.BusStopLine;
import pl.nextleveldev.smart_route.infrastructure.um.UmWarsawClient;
import pl.nextleveldev.smart_route.infrastructure.um.api.BusLineResponseException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
class LineImporter {

    private final UmWarsawClient umWarsawClient;
    private final BusStopRepository busStopRepository;
    private final LineRepository lineRepository;
    private final BusStopLineRepository busStopLineRepository;

    private final Set<String> processedLines = ConcurrentHashMap.newKeySet();

    @Scheduled(cron = "${bus-stops.updater.cron}")
    public void importLines() {
        log.info("Updating bus stop lines...");

        List<BusStop> all = busStopRepository.findAll();

        ExecutorService executor = Executors.newFixedThreadPool(4);

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

    public void eachBusStopUpdate(BusStop busStop) {
        log.info("Updating bus stop: {}-{}", busStop.getStopId(), busStop.getStopNr());

        busStopLineRepository.deleteAllByBusStop(busStop);

        try {
            var response = umWarsawClient.getBusLineFor(busStop.getStopId(), busStop.getStopNr());

            response.lines().forEach(line -> {
                synchronized (processedLines) {
                    if (!processedLines.contains(line)) {
                        Optional<Line> byLineIdentifier = lineRepository.findByLineIdentifier(line);

                        if (byLineIdentifier.isPresent()) {
                            busStopLineRepository.save(new BusStopLine(byLineIdentifier.get(), busStop));
                        } else {
                            var newLine = lineRepository.save(Line.builder()
                                    .lineIdentifier(line)
                                    .build());
                            busStopLineRepository.save(new BusStopLine(newLine, busStop));
                        }

                        processedLines.add(line);
                    }
                }
            });
        } catch (BusLineResponseException e) {
            log.error("Error updating stop: {}-{}", busStop.getStopId(), busStop.getStopNr());
        }
        log.info("Bus stop {} updated.", busStop.getStopId());
    }
}
