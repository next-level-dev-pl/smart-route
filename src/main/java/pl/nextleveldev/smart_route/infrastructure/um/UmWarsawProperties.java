package pl.nextleveldev.smart_route.infrastructure.um;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("um.warsaw")
record UmWarsawProperties(
        String apiKey, String baseUrl, TimetableResource timetable, StoreResource store) {

    record TimetableResource(String resourcePath, String timetableId, String busLineId) {}

    record StoreResource(String resourcePath, String stopInfoId) {}
}
