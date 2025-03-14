package pl.nextleveldev.smart_route.infrastructure.um;

import java.util.List;
import pl.nextleveldev.smart_route.infrastructure.um.api.BusLineResponseException;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmBusLineResponse;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmWarsawGenericResponse;

class UmWarsawResponseMapper {

    static UmBusLineResponse mapBusLineResponse(
            String stopId, String stopNr, UmWarsawGenericResponse response) {
        try {
            List<String> lines =
                    response.result().stream()
                            .flatMap(resultValues -> resultValues.values().stream())
                            .filter(value -> "linia".equalsIgnoreCase(value.key()))
                            .map(UmWarsawGenericResponse.ResultValues.Value::value)
                            .toList();

            return new UmBusLineResponse(stopId, stopNr, lines);
        } catch (Exception e) {
            throw new BusLineResponseException(
                    "Failed to map response for stop Id:" + stopId + "and stop number:" + stopNr,
                    e);
        }
    }
}
