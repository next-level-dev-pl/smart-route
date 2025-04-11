package pl.nextleveldev.smart_route.busline;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import pl.nextleveldev.smart_route.busstop.BusStop;
import pl.nextleveldev.smart_route.busstop.BusStopRepository;
import pl.nextleveldev.smart_route.infrastructure.um.UmWarsawClient;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
class BusLineImporter {

    private final UmWarsawClient umWarsawClient;
    private final BusStopRepository busStopRepository;
    private final BusLineRepository busLineRepository;
    private final TransactionTemplate transactionTemplate;

    @Scheduled(cron = "${bus-stops.updater.cron}")
    public void updateBusStopLines() {
        log.info("Updating bus stop lines...");

        List<BusStop> all = busStopRepository.findAll();

        try (ExecutorService executor = Executors.newFixedThreadPool(10)) {
            for (BusStop busStop : all) {
                executor.submit(
                        () -> {
                            try {
                                transactionTemplate.execute(_ -> {
                                    eachBusStopUpdate(busStop);
                                    return null;
                                });
                            } catch (Exception e) {
                                log.error("Error while updating stop {}", busStop.getStopId(), e);
                            }
                        });
            }
        }
    }

    private void eachBusStopUpdate(BusStop busStop) {
        log.info("Updating bus stop {} and {}", busStop.getStopId(), busStop.getStopNr());

        var response = umWarsawClient.getBusLineFor(busStop.getStopId(), busStop.getStopNr());

        response.lines()
                .forEach(
                        line -> {
                            var busLine = new BusLine(line, busStop);
                            busLineRepository.save(busLine);
                        });

        log.info("Bus stop {} updated.", busStop.getStopId());
    }
}
