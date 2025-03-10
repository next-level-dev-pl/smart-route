package pl.nextleveldev.smart_route.infrastructure.um;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmTimetableResponse;

@Slf4j
@RequiredArgsConstructor
public class UmWarsawClient {

    private final RestClient umWarsawClient;
    private final UmWarsawProperties properties;

    public UmTimetableResponse getTimetableFor(String stopId, String stopNr, String line) {
        return umWarsawClient
                .get()
                .uri(
                        urlBuilder ->
                                urlBuilder
                                        .scheme("https")
                                        .path(properties.timetable().resourcePath())
                                        .queryParam("id", properties.timetable().timetableId())
                                        .queryParam("busstopId", stopId)
                                        .queryParam("busstopNr", stopNr)
                                        .queryParam("line", line)
                                        .queryParam("apikey", properties.apiKey())
                                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(UmTimetableResponse.class);
    }

    public UmWarsawBusStopGenericResponse getBusLineFor(String stopId, String stopNr) {
        return umWarsawClient
                .get()
                .uri(
                        urlBuilder ->
                                urlBuilder
                                        .scheme("https")
                                        .path(properties.timetable().resourcePath())
                                        .queryParam("id", properties.timetable().busLineId())
                                        .queryParam("busstopId", stopId)
                                        .queryParam("busstopNr", stopNr)
                                        .queryParam("apikey", properties.apiKey())
                                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(UmWarsawBusStopGenericResponse.class);
    }

    public UmWarsawStopInfoGenericResponse getStopInfo() {
        return umWarsawClient
                .get()
                .uri(
                        urlBuilder ->
                                urlBuilder
                                        .scheme("https")
                                        .path(properties.store().resourcePath())
                                        .queryParam("id", properties.store().stopInfoId())
                                        .queryParam("apikey", properties.apiKey())
                                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(UmWarsawStopInfoGenericResponse.class);
    }

    public record UmWarsawBusStopGenericResponse(List<ResultValues> result) {}

    public record UmWarsawStopInfoGenericResponse(List<ResultValues> result) {}

    record ResultValues(List<Value> values) {}

    record Value(String key, String value) {}
}
