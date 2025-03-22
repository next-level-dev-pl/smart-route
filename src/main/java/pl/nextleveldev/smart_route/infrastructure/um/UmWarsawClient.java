package pl.nextleveldev.smart_route.infrastructure.um;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import pl.nextleveldev.smart_route.infrastructure.um.api.BusLineResponseException;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmBusLineResponse;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmStopInfoResponse;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UmWarsawClient {

    private final RestClient umWarsawClient;
    private final UmWarsawProperties properties;

    public UmWarsawRawResponses.Timetable getTimetableFor(
            String stopId, String stopNr, String line) {
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
                .body(UmWarsawRawResponses.Timetable.class);
    }

    public UmBusLineResponse getBusLineFor(String stopId, String stopNr) {
        try {
            UmWarsawRawResponses.BusLine response =
                    umWarsawClient
                            .get()
                            .uri(
                                    urlBuilder ->
                                            urlBuilder
                                                    .scheme("https")
                                                    .path(properties.timetable().resourcePath())
                                                    .queryParam(
                                                            "id",
                                                            properties.timetable().busLineId())
                                                    .queryParam("busstopId", stopId)
                                                    .queryParam("busstopNr", stopNr)
                                                    .queryParam("apikey", properties.apiKey())
                                                    .build())
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .body(UmWarsawRawResponses.BusLine.class);
            return UmWarsawResponseMapper.mapBusLineResponse(stopId, stopNr, response);
        } catch (RestClientException e) {
            throw new BusLineResponseException(
                    "Failed to receive response for stop Id:"
                            + stopId
                            + "and stop number:"
                            + stopNr,
                    e);
        }
    }

    public List<UmStopInfoResponse> getStopInfo() {
        try {
            UmWarsawRawResponses.StopInfo response =
                    umWarsawClient
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
                            .body(UmWarsawRawResponses.StopInfo.class);
            return UmWarsawResponseMapper.mapStopInfoResponse(response);
        } catch (RestClientException e) {
            throw new BusLineResponseException(
                    "Failed to receive response for stops info. " + e.getMessage(), e.getCause());
        }
    }
}
