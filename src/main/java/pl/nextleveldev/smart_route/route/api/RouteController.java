package pl.nextleveldev.smart_route.route.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/route")
@RequiredArgsConstructor
public class RouteController {

    @GetMapping("/search")
    public ResponseEntity<Void> searchRoute(
            @RequestParam("start") String start,
            @RequestParam("destination") String destination
    ) {
        return ResponseEntity.ok().build();
    }
}
