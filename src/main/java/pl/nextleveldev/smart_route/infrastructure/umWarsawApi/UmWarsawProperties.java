package pl.nextleveldev.smart_route.infrastructure.umWarsawApi;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
class UmWarsawProperties {

    @Value("${um.warsaw.api-key}")
    private String apiKey;

    @Value("${um.warsaw.base-url}")
    private String baseUrl;

    @Value("${um.warsaw.timetable-resource-url}")
    private String timetableResourceUrl;

    @Value("${um.warsaw.timetable-resource-id}")
    private String timetableResourceId;

    @Value("${um.warsaw.bus-line-on-stop-resource-id}")
    private String busLineOnStopResourceId;
}
