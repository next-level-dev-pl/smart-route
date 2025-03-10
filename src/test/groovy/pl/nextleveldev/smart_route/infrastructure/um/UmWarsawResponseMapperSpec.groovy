package pl.nextleveldev.smart_route.infrastructure.um

import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import pl.nextleveldev.smart_route.infrastructure.um.api.UmStopInfoResponse
import spock.lang.Specification

import java.time.LocalDateTime
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

    def "map stop info"() {
        given:
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        var date = LocalDateTime.parse("2024-12-14 00:00:00.0", formatter)
        GeometryFactory geometryFactory = new GeometryFactory();
        var location = geometryFactory.createPoint(new Coordinate(21.044827D, 52.248455D));
        location.setSRID(4326);

        def genericResponse = new UmWarsawStopInfoGenericResponse(
                [
                        new ResultValues(
                                [
                                        new Value("zespol", "1001"),
                                        new Value("slupek", "01"),
                                        new Value("nazwa_zespolu", "Kijowska"),
                                        new Value("id_ulicy", "2201"),
                                        new Value("szer_geo", "52.248455"),
                                        new Value("dlug_geo", "21.044827"),
                                        new Value("kierunek", "al.Zieleniecka"),
                                        new Value("obowiazuje_od", "2024-12-14 00:00:00.0")
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

}
