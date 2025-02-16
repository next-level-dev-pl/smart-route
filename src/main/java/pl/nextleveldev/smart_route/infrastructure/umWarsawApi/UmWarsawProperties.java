package pl.nextleveldev.smart_route.infrastructure.umWarsawApi;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "um.warsaw")
@Getter
@Setter
public class UmWarsawProperties {

    private String apiKey;
    private String baseUrl;
    private String timetableResourceUrl;
    private String timetableResourceId;
    private String busLineOnStop;
}
