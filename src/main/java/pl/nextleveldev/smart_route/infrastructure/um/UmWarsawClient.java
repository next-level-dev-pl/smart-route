package pl.nextleveldev.smart_route.infrastructure.um;

import static pl.nextleveldev.smart_route.infrastructure.um.UmWarsawResponseMapper.mapBusLineResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmBusLineResponse;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmStopInfoResponse;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmTimetableResponse;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmWarsawGenericResponse;

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

    public UmBusLineResponse getBusLineFor(String stopId, String stopNr) {
        UmWarsawGenericResponse genericResponse =
                umWarsawClient
                        .get()
                        .uri(
                                urlBuilder ->
                                        urlBuilder
                                                .scheme("https")
                                                .path(properties.timetable().resourcePath())
                                                .queryParam(
                                                        "id", properties.timetable().busLineId())
                                                .queryParam("busstopId", stopId)
                                                .queryParam("busstopNr", stopNr)
                                                .queryParam("apikey", properties.apiKey())
                                                .build())
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .body(UmWarsawGenericResponse.class);

        if (genericResponse != null) {
            return mapBusLineResponse(stopId, stopNr, genericResponse);
        } else
            throw new BusLineResponseException(
                    "Failed to retrieve bus line for stop ID: "
                            + stopId
                            + " and stop number: "
                            + stopNr);
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
}
