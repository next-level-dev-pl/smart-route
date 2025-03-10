package pl.nextleveldev.smart_route.infrastructure.um

import spock.lang.Specification

import static pl.nextleveldev.smart_route.infrastructure.um.UmWarsawClient.*
import static pl.nextleveldev.smart_route.infrastructure.um.UmWarsawClient.UmWarsawBusStopGenericResponse

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

}
