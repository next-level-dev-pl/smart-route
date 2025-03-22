package pl.nextleveldev.smart_route.infrastructure.um

import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class UmWarsawResponseMapperSpec extends Specification {

    def "map lines of buses for bus stop"() {
        given:
        def response = new UmWarsawRawResponses.BusLine(
                [
                        new UmWarsawRawResponses.BusLine.ResultValue(
                                [
                                        new UmWarsawRawResponses.BusLine.ResultValue.Value("linia", "250"),
                                        new UmWarsawRawResponses.BusLine.ResultValue.Value("unknown", "404"),
                                ]
                        ),
                        new UmWarsawRawResponses.BusLine.ResultValue(
                                [
                                        new UmWarsawRawResponses.BusLine.ResultValue.Value("linia", "520"),
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

        def genericResponse = new UmWarsawRawResponses.StopInfo(
                [
                        new UmWarsawRawResponses.StopInfo.ResultValue(
                                [
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("zespol", "1001"),
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("slupek", "01"),
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("nazwa_zespolu", "Kijowska"),
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("id_ulicy", "2201"),
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("szer_geo", "52.248455"),
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("dlug_geo", "21.044827"),
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("kierunek", "al.Zieleniecka"),
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("obowiazuje_od", "2024-12-14 00:00:00.0")
                                ]
                        ),
                        new UmWarsawRawResponses.StopInfo.ResultValue(
                                [
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("zespol", "R-01"),
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("slupek", "01"),
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("nazwa_zespolu", "Kijowska"),
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("id_ulicy", "null"),
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("szer_geo", "52.248455"),
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("dlug_geo", "21.044827"),
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("kierunek", "al.Zieleniecka"),
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("obowiazuje_od", "2024-12-14 00:00:00.0")
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

        def genericResponse = new UmWarsawRawResponses.StopInfo(
                [
                        new UmWarsawRawResponses.StopInfo.ResultValue(
                                [
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("zespol", "R-01"),
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("slupek", "01"),
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("nazwa_zespolu", "Kijowska"),
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("id_ulicy", "null"),
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("szer_geo", "52.248455"),
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("dlug_geo", "21.044827"),
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("kierunek", "al.Zieleniecka"),
                                        new UmWarsawRawResponses.StopInfo.ResultValue.Value("obowiazuje_od", "2024-12-14 00:00:00.0")
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

}
