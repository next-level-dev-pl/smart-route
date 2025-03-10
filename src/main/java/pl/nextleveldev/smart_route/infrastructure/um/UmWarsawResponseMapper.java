package pl.nextleveldev.smart_route.infrastructure.um;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import pl.nextleveldev.smart_route.infrastructure.um.UmWarsawClient.UmWarsawBusStopGenericResponse;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmBusLineResponse;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmStopInfoResponse;

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

    static List<UmStopInfoResponse> mapStopInfoResponse(UmWarsawStopInfoGenericResponse genericResponse) {
        var mappedResponse = new ArrayList<UmStopInfoResponse>();

        List<Map<String, String>> parsedJson = new ArrayList<>();
        genericResponse.result()
                .stream()
                .map(ResultValues::values)
                .forEach(resultValues -> {
                    Map<String, String> map = new HashMap<>();
                    resultValues.forEach(value -> {
                        map.put(value.key(), value.value());
                    });
                    parsedJson.add(map);
                });

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        parsedJson
                .forEach(parsedJsonElement -> {


                    String  stopId = parsedJsonElement.get("zespol") != null
                            ? parsedJsonElement.get("zespol")
                            : "0000";

                    String stopNr = parsedJsonElement.get("slupek") != null
                            ? parsedJsonElement.get("slupek")
                            : "01";

                    String stopIdName = parsedJsonElement.get("nazwa_zespolu") != null
                            ? parsedJsonElement.get("nazwa_zespolu")
                            : "Unknown";

                    Integer streetId = parsedJsonElement.get("id_ulicy") != null
                            ? Integer.parseInt(parsedJsonElement.get("id_ulicy"))
                            : null;

                    Double longitude = parsedJsonElement.get("dlug_geo") != null
                            ? Double.parseDouble(parsedJsonElement.get("dlug_geo"))
                            : 0;

                    Double latitude = parsedJsonElement.get("szer_geo") != null
                            ? Double.parseDouble(parsedJsonElement.get("szer_geo"))
                            : 0;

                    String direction = parsedJsonElement.get("kierunek") != null
                            ? parsedJsonElement.get("kierunek")
                            : "Unknown";

                    LocalDateTime validFrom = parsedJsonElement.get("obowiazuje_od") != null
                            ? LocalDateTime.parse(parsedJsonElement.get("obowiazuje_od"), formatter)
                            : LocalDateTime.now();

                    GeometryFactory geometryFactory = new GeometryFactory();
                    var currentStop = new UmStopInfoResponse(stopId, stopNr, stopIdName, streetId, new Point(), direction, validFrom);

                    mappedResponse.add(currentStop);
                });
        return mappedResponse;
    }
}
