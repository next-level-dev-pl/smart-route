package pl.nextleveldev.smart_route.infrastructure.um;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import pl.nextleveldev.smart_route.infrastructure.um.api.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pl.nextleveldev.smart_route.infrastructure.um.api.UmTimetableResponse.Result;

class UmWarsawResponseMapper {

    static UmBusLineResponse mapBusLineResponse(
            String stopId, String stopNr, UmWarsawRawResponses.BusLine response) {
        try {
            List<String> lines =
                    response.results().stream()
                            .flatMap(resultValue -> resultValue.values().stream())
                            .filter(value -> "linia".equalsIgnoreCase(value.key()))
                            .map(UmWarsawRawResponses.BusLine.ResultValue.Value::value)
                            .toList();

            return new UmBusLineResponse(stopId, stopNr, lines);
        } catch (Exception e) {
            throw new BusLineResponseException(
                    "Failed to map response for stop Id:" + stopId + "and stop number:" + stopNr,
                    e);
        }
    }

    static List<UmStopInfoResponse> mapStopInfoResponse(
            UmWarsawRawResponses.StopInfo genericResponse) {
        var mappedResponse = new ArrayList<UmStopInfoResponse>();

        try {
            List<Map<String, String>> parsedJson = new ArrayList<>();
            genericResponse.results().stream()
                    .map(UmWarsawRawResponses.StopInfo.ResultValue::values)
                    .forEach(
                            resultValues -> {
                                Map<String, String> map = new HashMap<>();
                                resultValues.forEach(
                                        value -> {
                                            map.put(value.key(), value.value());
                                        });
                                parsedJson.add(map);
                            });

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            GeometryFactory geometryFactory = new GeometryFactory();
            parsedJson.forEach(
                    parsedJsonElement -> {
                        String stopId = parsedJsonElement.get("zespol");

                        String stopNr = parsedJsonElement.get("slupek");

                        String stopIdName =
                                !parsedJsonElement.get("nazwa_zespolu").equals("null")
                                        ? parsedJsonElement.get("nazwa_zespolu")
                                        : null;

                        Integer streetId =
                                !parsedJsonElement.get("id_ulicy").equals("null")
                                        ? Integer.parseInt(parsedJsonElement.get("id_ulicy"))
                                        : null;

                        double longitude = Double.parseDouble(parsedJsonElement.get("dlug_geo"));

                        double latitude = Double.parseDouble(parsedJsonElement.get("szer_geo"));

                        String direction =
                                !parsedJsonElement.get("kierunek").equals("null")
                                        ? parsedJsonElement.get("kierunek")
                                        : null;

                        LocalDateTime validFrom =
                                !parsedJsonElement.get("obowiazuje_od").equals("null")
                                        ? LocalDateTime.parse(
                                        parsedJsonElement.get("obowiazuje_od"), formatter)
                                        : null;

                        Point location =
                                geometryFactory.createPoint(new Coordinate(longitude, latitude));
                        location.setSRID(4326);

                        var currentStop =
                                new UmStopInfoResponse(
                                        stopId,
                                        stopNr,
                                        stopIdName,
                                        streetId,
                                        location,
                                        direction,
                                        validFrom);

                        mappedResponse.add(currentStop);
                    });
            return mappedResponse;
        } catch (Exception e) {
            throw new StopInfoResponseException(
                    "Failed to map response for stops info. " + e.getMessage(), e);
        }
    }

    static UmTimetableResponse mapTimetableResponse(
            String stopId,
            String stopNr,
            String line,
            UmWarsawRawResponses.Timetable genericResponse) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            List<Result> results = new ArrayList<>();
            List<Map<String, String>> parsedJson = new ArrayList<>();
            genericResponse
                    .result()
                    .forEach(
                            values -> {
                                Map<String, String> eachResult = new HashMap<>();
                                values.forEach(
                                        value -> {
                                            eachResult.put(value.key(), value.value());
                                        });
                                parsedJson.add(eachResult);
                            });

            parsedJson.forEach(
                    parsedJsonElement -> {
                        String symbolOne =
                                parsedJsonElement.get("symbol_1") != null
                                        ? parsedJsonElement.get("symbol_1")
                                        : null;

                        String symbolTwo =
                                parsedJsonElement.get("symbol_2") != null
                                        ? parsedJsonElement.get("symbol_2")
                                        : null;

                        Integer brigade =
                                parsedJsonElement.get("brygada") != null
                                        ? Integer.parseInt(parsedJsonElement.get("brygada"))
                                        : null;

                        String direction =
                                parsedJsonElement.get("kierunek") != null
                                        ? parsedJsonElement.get("kierunek")
                                        : null;

                        String route =
                                parsedJsonElement.get("trasa") != null
                                        ? parsedJsonElement.get("trasa")
                                        : null;

                        LocalTime time =
                                parsedJsonElement.get("czas") != null
                                        ? LocalTime.parse(
                                        parsedJsonElement.get("czas").startsWith("24:")
                                                ? "00:"
                                                + parsedJsonElement
                                                .get("czas")
                                                .substring(3)
                                                : parsedJsonElement.get("czas"),
                                        formatter)
                                        : null;
                        results.add(
                                new Result(symbolOne, symbolTwo, brigade, direction, route, time));
                    });

            return new UmTimetableResponse(stopId, stopNr, line, results);
        } catch (Exception e) {
            throw new TimetableResponseException(
                    "Failed to map response for timetable for stop Id:"
                            + stopId
                            + " and stop number: "
                            + stopNr
                            + " and line: "
                            + line,
                    e);
        }
    }
}
