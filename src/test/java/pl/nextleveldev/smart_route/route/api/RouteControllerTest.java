package pl.nextleveldev.smart_route.route.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.nextleveldev.smart_route.route.RouteService;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouteControllerTest {

    public static final String START_LOCATION = "Marszałkowska 10";
    public static final String DESTINATION = "Wałbrzyska 7";
    public static final String API_ERROR = "API error";

    @InjectMocks
    private RouteController routeController;

    @Mock
    private RouteService routeService;

    @Test
    void shouldReturnStatus200_WhenRouteFound() {
        // Given
        String startLocation = START_LOCATION;
        String destination = DESTINATION;
        List<Object> mockVehicles = List.of("Data from an external API");

        when(routeService.searchRoute(startLocation, destination)).thenReturn(mockVehicles);

        // When
        ResponseEntity<List<?>> response = routeController.searchRoute(startLocation, destination);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnStatus204_WhenNoRouteFound() {
        // Given
        String startLocation = START_LOCATION;
        String destination = DESTINATION;

        when(routeService.searchRoute(startLocation, destination)).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<?>> response = routeController.searchRoute(startLocation, destination);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void shouldReturnStatus500_WhenExceptionOccurs() {
        // Given
        String startLocation = START_LOCATION;
        String destination = DESTINATION;

        when(routeService.searchRoute(anyString(), anyString())).thenThrow(new RuntimeException(API_ERROR));

        // When & Then
        assertThatThrownBy(() -> routeController.searchRoute(startLocation, destination))
                .isInstanceOf(RuntimeException.class)
                .hasMessage(API_ERROR);
    }
}
