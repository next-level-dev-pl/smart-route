package pl.nextleveldev.smart_route.busstop;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.nextleveldev.smart_route.infrastructure.um.UmWarsawClient;
import pl.nextleveldev.smart_route.utils.Gatherers;

@Component
@RequiredArgsConstructor
class BusStopImporter {

    private final UmWarsawClient umWarsawClient;
    private final BusStopRepository busStopRepository;

    @Scheduled(cron = "${bus-stops.importer.cron}")
    public void importBusStops() {
        var response = umWarsawClient.getStopInfo();

        response.stream()
                .map(stopInfo -> new BusStop(
                        stopInfo.stopId(),
                        stopInfo.stopNr(),
                        stopInfo.stopIdName(),
                        stopInfo.streetId().toString(),
                        stopInfo.location(),
                        stopInfo.direction(),
                        stopInfo.validFrom()
                ))
                .collect(Gatherers.groupingByFixedSize(10))
                .forEach(busStopRepository::saveAll);
    }


}
