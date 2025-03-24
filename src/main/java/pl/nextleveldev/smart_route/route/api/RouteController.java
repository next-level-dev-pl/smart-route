package pl.nextleveldev.smart_route.route.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
class RouteController {

    @GetMapping
    public ResponseEntity<?> searchRoute(
            @RequestParam("start") String start, @RequestParam("destination") String destination) {
        log.debug("searchRoute start: {}, destination: {}", start, destination);
        return ResponseEntity.ok().build();
    }
}
