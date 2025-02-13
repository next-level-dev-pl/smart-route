package pl.nextleveldev.smart_route.infrastructure.um;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class UmWarsawClient {

    private final String BASE_URL = "https://api.umwarsaw.pl"; // Adres API
    private final RestTemplate restTemplate;

    public UmWarsawClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //  - pobranie informacji o mieście
    public String getCityInfo() {
        String url = BASE_URL + "/city/info"; 
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            return response.getBody();
        } catch (Exception e) {
            throw new UmWarsawClientException("Błąd podczas komunikacji z API", e);
        }
    }


}
