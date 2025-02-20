package pl.nextleveldev.smart_route.infrastructure.um;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("um.warsaw")
record UmWarsawProperties(
        String apiKey,
        String baseUrl,
        TimetableResource timetable,
        BusLineResource busLine
) {

    record TimetableResource(
            String resourcePath,
            String resourceId
    ) {
    }
    record BusLineResource(
            String resourceId
    ) {
    }
}
