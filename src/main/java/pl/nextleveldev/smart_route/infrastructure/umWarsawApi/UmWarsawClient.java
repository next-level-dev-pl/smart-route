package pl.nextleveldev.smart_route.infrastructure.umWarsawApi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
class UmWarsawClient implements UmWarsawAPI {

    private final WebClient umWarsawWebClient;
    private final UmWarsawProperties properties;

    @Override
    public String getSupportedBusLinesAtStop(String stopId, String stopNr) {

        return umWarsawWebClient.get()
                .uri(urlBuilder -> urlBuilder.scheme("https")
                        .path(properties.getTimetableResourceUrl())
                        .queryParam("id", properties.getBusLineOnStopResourceId())
                        .queryParam("busstopId", stopId)
                        .queryParam("busstopNr", stopNr)
                        .queryParam("apikey", properties.getApiKey())
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
