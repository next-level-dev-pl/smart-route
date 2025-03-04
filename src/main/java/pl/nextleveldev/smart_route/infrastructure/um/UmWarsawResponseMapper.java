package pl.nextleveldev.smart_route.infrastructure.um;

import java.util.List;
import pl.nextleveldev.smart_route.infrastructure.um.api.KeyValue;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmBusLineResponse;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmWarsawGenericResponse;

class UmWarsawResponseMapper {

    static UmBusLineResponse mapBusLineResponse(
            String stopId, String stopNr, UmWarsawGenericResponse response) {
        List<String> lines =
                response.result().stream()
                        .flatMap(resultValues -> resultValues.values().stream())
                        .filter(value -> "linia".equalsIgnoreCase(value.key()))
                        .map(KeyValue::value)
                        .toList();

        return new UmBusLineResponse(stopId, stopNr, lines);
    }
}
