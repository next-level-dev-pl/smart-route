package pl.nextleveldev.smart_route.infrastructure.um;

import org.apache.hc.client5.http.impl.DefaultRedirectStrategy;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(UmWarsawProperties.class)
class UmWarsawConfig {

    @Bean
    public UmWarsawClient umWarsawClient(
            UmWarsawProperties umWarsawProperties
    ) {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setRedirectStrategy(new DefaultRedirectStrategy())
                .build();
        RestClient restClient = RestClient.builder()
                .baseUrl(umWarsawProperties.baseUrl())
                .requestFactory(new HttpComponentsClientHttpRequestFactory(httpClient))
                .build();
        return new UmWarsawClient(
                restClient,
                umWarsawProperties
        );
    }

}
