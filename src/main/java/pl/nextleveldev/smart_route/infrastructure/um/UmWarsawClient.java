package pl.nextleveldev.smart_route.infrastructure.um;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import pl.nextleveldev.smart_route.infrastructure.um.api.*;

@Slf4j
@RequiredArgsConstructor
public class UmWarsawClient {

    private final RestClient umWarsawClient;
    private final UmWarsawProperties properties;

    public UmTimetableResponse getTimetableFor(
            String stopId, String stopNr, String line) {
        try {
            UmWarsawRawResponses.Timetable response =
            umWarsawClient
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
            return UmWarsawResponseMapper.mapTimetableResponse(stopId, stopNr, line, response);
        } catch (RestClientException e) {
            throw new TimetableResponseException(
                    "Failed to receive response for timetable for stop Id:"
                            + stopId
                            + " and stop number: "
                            + stopNr
                            + " and line: "
                            + line,
                    e);
        }
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
                                                    .queryParam(
                                                            "id", properties.store().stopInfoId())
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
