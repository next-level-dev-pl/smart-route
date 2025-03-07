package pl.nextleveldev.smart_route.infrastructure.um;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmStopInfoResponse;
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

    public UmWarsawGenericResponse getBusLineFor(String stopId, String stopNr) {
        try {
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
                    .body(UmWarsawGenericResponse.class);

        } catch (RestClientException e) {
            throw new BusLineResponseException(
                    "Failed to receive response for stop Id:"
                            + stopId
                            + "and stop number:"
                            + stopNr
                            + ". Error: "
                            + e.getMessage());
        }
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

    public record UmWarsawGenericResponse(List<ResultValues> result) {
        record ResultValues(List<Value> values) {}

        record Value(String key, String value) {}
    }
}
