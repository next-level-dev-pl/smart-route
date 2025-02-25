package pl.nextleveldev.smart_route.infrastructure.um;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmBusLineResponse;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmStopInfoResponse;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmTimetableResponse;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UmWarsawClient {

    private final WebClient umWarsawWebClient;
    private final UmWarsawProperties properties;
    private final ObjectMapper objectMapper;

    public UmTimetableResponse getTimetableFor(String stopId, String stopNr, String line) {
        return umWarsawWebClient.get()
                .uri(urlBuilder -> urlBuilder.scheme("https")
                        .path(properties.timetable().resourcePath())
                        .queryParam("id", properties.timetable().timetableId())
                        .queryParam("busstopId", stopId)
                        .queryParam("busstopNr", stopNr)
                        .queryParam("line", line)
                        .queryParam("apikey", properties.apiKey())
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .map(json -> {
                    // TODO: add specific response mapping
                    log.debug("response: {}", json);
                    try {
                        return objectMapper.readValue(json, UmTimetableResponse.class);
                    } catch (JsonProcessingException e) {
                        // TODO: prepare proper exceptions
                        throw new RuntimeException(e);
                    }
                })
                .block();
    }

    public UmBusLineResponse getBusLineFor(String stopId, String stopNr) {
        return umWarsawWebClient.get()
                .uri(urlBuilder -> urlBuilder.scheme("https")
                        .path(properties.timetable().resourcePath())
                        .queryParam("id", properties.timetable().busLineId())
                        .queryParam("busstopId", stopId)
                        .queryParam("busstopNr", stopNr)
                        .queryParam("apikey", properties.apiKey())
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .map(json -> {
                    try {
                        List<String> lines = new ArrayList<>();
                        BeforeParseUmBusLineResponse umBusLineResponse = objectMapper.readValue(json, BeforeParseUmBusLineResponse.class);
                        umBusLineResponse.result().stream()
                                .map(BeforeParseUmBusLineResponse.ResultValues::values)
                                .forEach(value -> lines.add(value.getFirst().value()));

                        return new UmBusLineResponse(stopId, stopNr, lines);
                    } catch (JsonProcessingException e) {
                        throw new BusLineParsingException(e.getMessage());
                    }
                })
                .block();
    }

    public UmStopInfoResponse getStopInfo() {
        return umWarsawWebClient.get()
                .uri(urlBuilder -> urlBuilder.scheme("https")
                        .path(properties.store().resourcePath())
                        .queryParam("id", properties.store().stopInfoId())
                        .queryParam("apikey", properties.apiKey())
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .map(json -> {
                    // TODO: add specific response mapping
                    log.debug("response: {}", json);
                    try {
                        return objectMapper.readValue(json, UmStopInfoResponse.class);
                    } catch (JsonProcessingException e) {
                        // TODO: prepare proper exceptions
                        throw new RuntimeException(e);
                    }
                })
                .block();
    }

    record BeforeParseUmBusLineResponse(
            List<ResultValues> result
    ) {
        record ResultValues(
                List<Value> values
        ) {
        }
        record Value(
                String key,
                String value
        ) {
        }
    }
}
