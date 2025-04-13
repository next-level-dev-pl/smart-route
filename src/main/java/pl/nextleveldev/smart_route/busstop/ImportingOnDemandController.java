package pl.nextleveldev.smart_route.busstop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/import")
@RequiredArgsConstructor
class ImportingOnDemandController {

    private final BusStopImporter busStopImporter;
    private final LineImporter lineImporter;

    @PostMapping
    @RequestMapping("/bus-stops")
    public ResponseEntity<?> importBusStopsOnDemand() {
        log.debug("started import of bus stops on demand");
        busStopImporter.importBusStops();
        log.debug("ended import of bus stops on demand");
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @RequestMapping("/bus-lines")
    public ResponseEntity<?> importBusLinesOnDemand() {
        log.debug("started import of bus lines on demand");
        lineImporter.importLines();
        log.debug("ended import of bus lines on demand");
        return ResponseEntity.ok().build();
    }
}
