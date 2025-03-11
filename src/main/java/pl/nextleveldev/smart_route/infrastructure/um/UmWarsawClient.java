package pl.nextleveldev.smart_route.infrastructure.um;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmStopInfoResponse;

@Slf4j
@RequiredArgsConstructor
public class UmWarsawClient {

    private final RestClient umWarsawClient;
    private final UmWarsawProperties properties;

    public UmWarsawTimetableGenericResponse getTimetableFor(
            String stopId, String stopNr, String line) {
        try {
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
                    .body(UmWarsawTimetableGenericResponse.class);
        } catch (RestClientException e) {
            throw new TimetableResponseException(
                    "Failed to receive response for timetable for stop Id:"
                            + stopId
                            + " and stop number: "
                            + stopNr
                            + " and line: "
                            + line
                            + ". Error: "
                            + e.getMessage());
        }
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

    public UmStopInfoResponse getStopInfo() {
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
                .body(UmStopInfoResponse.class);
    }

    public record UmWarsawBusStopGenericResponse(List<ResultValues> result) {}

    public record UmWarsawTimetableGenericResponse(List<List<Value>> result) {}

    record ResultValues(List<Value> values) {}

    record Value(String key, String value) {}
}
