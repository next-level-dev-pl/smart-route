package pl.nextleveldev.smart_route.route.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import pl.nextleveldev.smart_route.route.RouteService;

import java.util.List;

@RestController
@RequestMapping("/route")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @GetMapping("/search")
    public ResponseEntity<List<?>> searchRoute(
            @RequestParam String startLocation,
            @RequestParam String destination) {
        List<?> vehicles = routeService.searchRoute(startLocation, destination);

        if (vehicles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(vehicles);
    }
}
