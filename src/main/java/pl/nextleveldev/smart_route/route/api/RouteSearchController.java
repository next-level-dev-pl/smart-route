package pl.nextleveldev.smart_route.route.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/route")
public class RouteSearchController {

    @GetMapping("/search")
    public ResponseEntity<String> searchRoute(@RequestParam String from, @RequestParam String to) {
        // Placeholder response
        return ResponseEntity.ok("Searching route from " + from + " to " + to);
    }
}
