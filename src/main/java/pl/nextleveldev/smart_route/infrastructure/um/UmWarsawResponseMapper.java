package pl.nextleveldev.smart_route.infrastructure.um;

import pl.nextleveldev.smart_route.infrastructure.um.UmWarsawClient.UmWarsawGenericResponse;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmBusLineResponse;

import java.util.List;

class UmWarsawResponseMapper {

    static UmBusLineResponse mapBusLineResponse(String stopId, String stopNr, UmWarsawGenericResponse response) {
        List<String> lines = response.result().stream()
                .flatMap(resultValues -> resultValues.values().stream())
                .filter(value -> value.key().equals("linia"))
                .map(UmWarsawGenericResponse.Value::value)
                .toList();

        return new UmBusLineResponse(stopId, stopNr, lines);
    }
}
