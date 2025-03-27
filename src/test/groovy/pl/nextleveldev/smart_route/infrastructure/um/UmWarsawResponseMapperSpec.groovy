package pl.nextleveldev.smart_route.infrastructure.um

import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import pl.nextleveldev.smart_route.infrastructure.um.api.UmTimetableResponse
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

import static pl.nextleveldev.smart_route.infrastructure.um.UmWarsawRawResponses.*

class UmWarsawResponseMapperSpec extends Specification {

    def "map lines of buses for bus stop"() {
        given:
        def response = new BusLine(
                [
                        new BusLine.ResultValue(
                                [
                                        new BusLine.ResultValue.Value("linia", "250"),
                                        new BusLine.ResultValue.Value("unknown", "404"),
                                ]
                        ),
                        new BusLine.ResultValue(
                                [
                                        new BusLine.ResultValue.Value("linia", "520"),
                                ]
                        )
                ]
        )

        when:
        def result = UmWarsawResponseMapper.mapBusLineResponse("123", "02", response)

        then:
        result.stopId() == "123"
        result.stopNr() == "02"
        result.lines() ==~ ["250", "520"]
    }

    def "map stop info"() {
        given:
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        var date = LocalDateTime.parse("2024-12-14 00:00:00.0", formatter)
        GeometryFactory geometryFactory = new GeometryFactory();
        var location = geometryFactory.createPoint(new Coordinate(21.044827D, 52.248455D));
        location.setSRID(4326);

        def genericResponse = new StopInfo(
                [
                        new StopInfo.ResultValue(
                                [
                                        new StopInfo.ResultValue.Value("zespol", "1001"),
                                        new StopInfo.ResultValue.Value("slupek", "01"),
                                        new StopInfo.ResultValue.Value("nazwa_zespolu", "Kijowska"),
                                        new StopInfo.ResultValue.Value("id_ulicy", "2201"),
                                        new StopInfo.ResultValue.Value("szer_geo", "52.248455"),
                                        new StopInfo.ResultValue.Value("dlug_geo", "21.044827"),
                                        new StopInfo.ResultValue.Value("kierunek", "al.Zieleniecka"),
                                        new StopInfo.ResultValue.Value("obowiazuje_od", "2024-12-14 00:00:00.0")
                                ]
                        ),
                        new StopInfo.ResultValue(
                                [
                                        new StopInfo.ResultValue.Value("zespol", "R-01"),
                                        new StopInfo.ResultValue.Value("slupek", "01"),
                                        new StopInfo.ResultValue.Value("nazwa_zespolu", "Kijowska"),
                                        new StopInfo.ResultValue.Value("id_ulicy", "null"),
                                        new StopInfo.ResultValue.Value("szer_geo", "52.248455"),
                                        new StopInfo.ResultValue.Value("dlug_geo", "21.044827"),
                                        new StopInfo.ResultValue.Value("kierunek", "al.Zieleniecka"),
                                        new StopInfo.ResultValue.Value("obowiazuje_od", "2024-12-14 00:00:00.0")
                                ]
                        )
                ]
        )
        when:
        def result = UmWarsawResponseMapper.mapStopInfoResponse(genericResponse)

        then:
        result.getFirst().stopId() == "1001"
        result.getFirst().stopNr() == "01"
        result.getFirst().stopIdName() == "Kijowska"
        result.getFirst().streetId() == 2201
        result.getFirst().location() == location
        result.getFirst().direction() == "al.Zieleniecka"
        result.getFirst().validFrom() == date
    }

    def "map stop info with null values"() {
        given:
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        var date = LocalDateTime.parse("2024-12-14 00:00:00.0", formatter)
        GeometryFactory geometryFactory = new GeometryFactory();
        var location = geometryFactory.createPoint(new Coordinate(21.044827D, 52.248455D));
        location.setSRID(4326);

        def genericResponse = new StopInfo(
                [
                        new StopInfo.ResultValue(
                                [
                                        new StopInfo.ResultValue.Value("zespol", "R-01"),
                                        new StopInfo.ResultValue.Value("slupek", "01"),
                                        new StopInfo.ResultValue.Value("nazwa_zespolu", "Kijowska"),
                                        new StopInfo.ResultValue.Value("id_ulicy", "null"),
                                        new StopInfo.ResultValue.Value("szer_geo", "52.248455"),
                                        new StopInfo.ResultValue.Value("dlug_geo", "21.044827"),
                                        new StopInfo.ResultValue.Value("kierunek", "al.Zieleniecka"),
                                        new StopInfo.ResultValue.Value("obowiazuje_od", "2024-12-14 00:00:00.0")
                                ]
                        )
                ]
        )
        when:
        def result = UmWarsawResponseMapper.mapStopInfoResponse(genericResponse)

        then:
        result.getFirst().stopId() == "R-01"
        result.getFirst().stopNr() == "01"
        result.getFirst().stopIdName() == "Kijowska"
        result.getFirst().streetId() == null
        result.getFirst().location() == location
        result.getFirst().direction() == "al.Zieleniecka"
        result.getFirst().validFrom() == date
    }

    def "map timetable response"() {
        given:
        String stopId = "7009"
        String stopNr = "01"
        String line = "520"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

        def genericResponse = new Timetable(
                [
                        [
                                new Timetable.Value("symbol_1", "null"),
                                new Timetable.Value("symbol_2", "null"),
                                new Timetable.Value("brygada", "2"),
                                new Timetable.Value("kierunek", "Dw. Centralny"),
                                new Timetable.Value("trasa", "TP-DWC"),
                                new Timetable.Value("czas", "08:17:00")
                        ],
                        [
                                new Timetable.Value("symbol_1", "null"),
                                new Timetable.Value("symbol_2", "null"),
                                new Timetable.Value("brygada", "09"),
                                new Timetable.Value("kierunek", "Dw. Centralny"),
                                new Timetable.Value("trasa", "TP-DWC"),
                                new Timetable.Value("czas", "24:45:00")
                        ]
                ]
        )

        when:
        UmTimetableResponse result = UmWarsawResponseMapper.mapTimetableResponse(stopId, stopNr, line, genericResponse)

        then:
        result.stopId() == stopId
        result.stopNr() == stopNr
        result.line() == line
        result.result().size() == 2

        with(result.result().get(0)) {
            symbolOne() == "null"
            symbolTwo() == "null"
            brigade() == 2
            direction() == "Dw. Centralny"
            route() == "TP-DWC"
            time() == LocalTime.parse("08:17:00", formatter)
        }

        with(result.result().get(1)) {
            symbolOne() == "null"
            symbolTwo() == "null"
            brigade() == 9
            direction() == "Dw. Centralny"
            route() == "TP-DWC"
            time() == LocalTime.parse("00:45:00", formatter)
        }
    }
}
