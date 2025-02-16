package pl.nextleveldev.smart_route.infrastructure.umWarsawApi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
class UmWarsawConfig {

    @Bean
    public UmWarsawClient umWarsawClient() {
        return new UmWarsawClient(umWarsawWebClient(), properties());
    }

    @Bean
    public WebClient umWarsawWebClient() {
        return WebClient.builder()
                .baseUrl(properties().getBaseUrl())
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().followRedirect(true)
                ))
                .build();
    }

    @Bean
    public UmWarsawProperties properties() {
        return new UmWarsawProperties();
    }
}
