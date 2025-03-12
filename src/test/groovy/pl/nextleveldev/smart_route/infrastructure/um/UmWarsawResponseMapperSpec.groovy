package pl.nextleveldev.smart_route.infrastructure.um

import pl.nextleveldev.smart_route.infrastructure.um.api.UmTimetableResponse
import spock.lang.Specification
import java.time.LocalTime
import java.time.format.DateTimeFormatter

import static pl.nextleveldev.smart_route.infrastructure.um.UmWarsawClient.*

class UmWarsawResponseMapperSpec extends Specification {

    def "map lines of buses for bus stop"() {
        given:
        def response = new UmWarsawBusStopGenericResponse(
                [
                        new ResultValues(
                                [
                                        new Value("linia", "250"),
                                        new Value("unknown", "250"),
                                ]
                        ),
                        new ResultValues(
                                [
                                        new Value("linia", "520"),
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

    def "map timetable response"() {
        given:
        String stopId = "7009"
        String stopNr = "01"
        String line = "520"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

        def genericResponse = new UmWarsawTimetableGenericResponse(
                [
                        [
                                new Value("symbol_1", "null"),
                                new Value("symbol_2", "null"),
                                new Value("brygada", "2"),
                                new Value("kierunek", "Dw. Centralny"),
                                new Value("trasa", "TP-DWC"),
                                new Value("czas", "08:17:00")
                        ],
                        [
                                new Value("symbol_1", "null"),
                                new Value("symbol_2", "null"),
                                new Value("brygada", "09"),
                                new Value("kierunek", "Dw. Centralny"),
                                new Value("trasa", "TP-DWC"),
                                new Value("czas", "24:45:00")
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
