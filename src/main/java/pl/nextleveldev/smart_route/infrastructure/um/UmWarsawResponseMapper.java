package pl.nextleveldev.smart_route.infrastructure.um;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import pl.nextleveldev.smart_route.infrastructure.um.api.BusLineResponseException;
import pl.nextleveldev.smart_route.infrastructure.um.api.StopInfoResponseException;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmBusLineResponse;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmStopInfoResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    "Failed to map response for stops info. " + e.getMessage());
        }
    }
}
