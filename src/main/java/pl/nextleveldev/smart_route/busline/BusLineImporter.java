package pl.nextleveldev.smart_route.busline;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.client.RestClientException;
import pl.nextleveldev.smart_route.busstop.BusStop;
import pl.nextleveldev.smart_route.busstop.BusStopRepository;
import pl.nextleveldev.smart_route.infrastructure.um.UmWarsawClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusLineImporter {

    private final UmWarsawClient umWarsawClient;
    private final BusStopRepository busStopRepository;
    private final BusLineRepository busLineRepository;
    private final TransactionTemplate transactionTemplate;

    @Scheduled(cron = "${bus-lines.importer.cron}")
    public void importBusLines() {
        log.info("Updating bus stop lines...");

        List<BusStop> all = busStopRepository.findAll();
        all.forEach(busStop -> busLineRepository.deleteAll(busStop.getBusLines()));

        try (ExecutorService executor = Executors.newFixedThreadPool(10)) {
            for (BusStop busStop : all) {
                executor.submit(
                        () -> {
                            try {
                                transactionTemplate.execute(
                                        _ -> {
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
        log.info("Updating bus stop: {}-{}", busStop.getStopId(), busStop.getStopNr());
        try {
            var response = umWarsawClient.getBusLineFor(busStop.getStopId(), busStop.getStopNr());
            Set<BusLine> busLines = ConcurrentHashMap.newKeySet();

            response.lines()
                    .forEach(
                            lineId -> {
                                BusLine existingBusLine =
                                        busLineRepository
                                                .findByLineIdentifier(lineId)
                                                .orElseGet(
                                                        () -> {
                                                            BusLine newBusLine = new BusLine();
                                                            newBusLine.setLineIdentifier(lineId);
                                                            return busLineRepository.save(
                                                                    newBusLine);
                                                        });
                                busLines.add(existingBusLine);
                            });

            busStop.setBusLines(busLines);
            busStopRepository.save(busStop);

        } catch (RestClientException e) {
            log.error("Error updating stop: {}-{}", busStop.getStopId(), busStop.getStopNr(), e);
        }
        log.info("Bus stop {} updated.", busStop.getStopId());
    }
}
