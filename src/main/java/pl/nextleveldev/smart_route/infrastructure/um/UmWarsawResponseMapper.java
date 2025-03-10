package pl.nextleveldev.smart_route.infrastructure.um;

import java.util.List;
import pl.nextleveldev.smart_route.infrastructure.um.UmWarsawClient.UmWarsawBusStopGenericResponse;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmBusLineResponse;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmTimetableResponse;

import static pl.nextleveldev.smart_route.infrastructure.um.UmWarsawClient.*;

class UmWarsawResponseMapper {

    static UmBusLineResponse mapBusLineResponse(
            String stopId, String stopNr, UmWarsawBusStopGenericResponse response) {
        try {
            List<String> lines =
                    response.result().stream()
                            .flatMap(resultValues -> resultValues.values().stream())
                            .filter(value -> "linia".equalsIgnoreCase(value.key()))
                            .map(Value::value)
                            .toList();

            return new UmBusLineResponse(stopId, stopNr, lines);
        } catch (Exception e) {
            throw new BusLineResponseException(
                    "Failed to map response for stop Id:"
                            + stopId
                            + "and stop number:"
                            + stopNr
                            + ". Error: "
                            + e.getMessage());
        }
    }

    static UmTimetableResponse mapTimetableResponse(String stopId, String stopNr, String line, UmWarsawTimetableGenericResponse genericResponse) {

    }
}
