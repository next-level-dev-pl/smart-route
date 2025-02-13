package pl.nextleveldev.smart_route.infrastructure.um;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UmWarsawController {

    private final UmWarsawClient umWarsawClient;

    public UmWarsawController(UmWarsawClient umWarsawClient) {
        this.umWarsawClient = umWarsawClient;
    }

    @GetMapping("/city-info")
    public String getCityInfo() {
        return umWarsawClient.getCityInfo();
    }
}
