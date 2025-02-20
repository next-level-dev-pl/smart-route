package pl.nextleveldev.smart_route.infrastructure.um;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmBusLineResponse;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmTimetableResponse;

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
                    // TODO: add specific response mapping
                    log.debug("response: {}", json);
                    try {
                        return objectMapper.readValue(json, UmBusLineResponse.class);
                    } catch (JsonProcessingException e) {
                        // TODO: prepare proper exceptions
                        throw new RuntimeException(e);
                    }
                })
                .block();
    }
}
