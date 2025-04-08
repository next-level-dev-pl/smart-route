package pl.nextleveldev.smart_route.busstop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.nextleveldev.smart_route.infrastructure.um.UmWarsawClient;
import pl.nextleveldev.smart_route.utils.Gatherers;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
class BusStopImporter {

    private final UmWarsawClient umWarsawClient;
    private final BusStopRepository busStopRepository;

    @Transactional
    @Scheduled(cron = "${bus-stops.importer.cron}")
    public void importBusStops() {
        log.info("Importing bus stops...");
        var response = umWarsawClient.getStopInfo();

        response.stream()
                .map(stopInfo -> new BusStop(
                        stopInfo.stopId(),
                        stopInfo.stopNr(),
                        stopInfo.stopIdName(),
                        Optional.ofNullable(stopInfo.streetId()).map(Objects::toString).orElse(null),
                        stopInfo.location(),
                        stopInfo.direction(),
                        stopInfo.validFrom()
                ))
                .collect(Gatherers.groupingByFixedSize(10))
                .forEach(busStopRepository::saveAll);

        log.info("Bus stops imported.");
    }


}
