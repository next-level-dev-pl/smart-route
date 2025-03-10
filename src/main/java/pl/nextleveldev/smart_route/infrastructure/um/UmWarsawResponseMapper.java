package pl.nextleveldev.smart_route.infrastructure.um;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nextleveldev.smart_route.infrastructure.um.UmWarsawClient.UmWarsawBusStopGenericResponse;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmBusLineResponse;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmTimetableResponse;

import static pl.nextleveldev.smart_route.infrastructure.um.UmWarsawClient.*;
import static pl.nextleveldev.smart_route.infrastructure.um.api.UmTimetableResponse.*;

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
        List<Map<String, String>> parsedJson = new ArrayList<>();
        genericResponse.result().stream()
                .map(ResultValues::values)
                .forEach(
                        resultValues -> {
                            Map<String, String> map = new HashMap<>();
                            resultValues.forEach(
                                    value -> {
                                        map.put(value.key(), value.value());
                                    });
                            parsedJson.add(map);
                        });

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        List<Result> results = new ArrayList<>();
        parsedJson.forEach(
                parsedJsonElement -> {
                    String symbolOne =
                            !parsedJsonElement.get("symbol_1").equals("null")
                                    ? parsedJsonElement.get("symbol_1")
                                    : null;

                    String symbolTwo =
                            !parsedJsonElement.get("symbol_2").equals("null")
                                    ? parsedJsonElement.get("symbol_2")
                                    : null;

                    Integer brigade =
                            !parsedJsonElement.get("brygada").equals("null")
                                    ? Integer.parseInt(parsedJsonElement.get("brygada"))
                                    : null;

                    String direction =
                            !parsedJsonElement.get("kierunek").equals("null")
                                    ? parsedJsonElement.get("kierunek")
                                    : null;

                    String route =
                            !parsedJsonElement.get("trasa").equals("null")
                                    ? parsedJsonElement.get("trase")
                                    : null;

                    LocalTime time =
                            !parsedJsonElement.get("czas").equals("null")
                                    ? LocalTime.parse(
                                    parsedJsonElement.get("czas"), formatter)
                                    : null;
                    results.add(new Result(symbolOne, symbolTwo, brigade, direction, route, time));
                }
        );

        return new UmTimetableResponse(stopId, stopNr, line, results);
    }
}
