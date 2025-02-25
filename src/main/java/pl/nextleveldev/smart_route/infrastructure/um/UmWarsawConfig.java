package pl.nextleveldev.smart_route.infrastructure.um;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
@EnableConfigurationProperties(UmWarsawProperties.class)
class UmWarsawConfig {

    @Bean
    public UmWarsawClient umWarsawClient(
            UmWarsawProperties umWarsawProperties,
            ObjectMapper objectMapper,
            UmWarsawResponseMapper responseMapper
    ) {
        WebClient webClient = WebClient.builder()
                .baseUrl(umWarsawProperties.baseUrl())
                .codecs(configurer -> configurer.defaultCodecs()
                        .maxInMemorySize(10 * 1024 * 1024))
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().followRedirect(true)
                ))
                .build();
        return new UmWarsawClient(
                webClient,
                umWarsawProperties,
                objectMapper,
                responseMapper
        );
    }

}
