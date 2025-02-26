package pl.nextleveldev.smart_route.infrastructure.um

import spock.lang.Specification

import static pl.nextleveldev.smart_route.infrastructure.um.UmWarsawClient.UmWarsawGenericResponse
import static pl.nextleveldev.smart_route.infrastructure.um.UmWarsawClient.UmWarsawGenericResponse.ResultValues
import static pl.nextleveldev.smart_route.infrastructure.um.UmWarsawClient.UmWarsawGenericResponse.Value

class UmWarsawResponseMapperSpec extends Specification {

    def "map lines of buses for bus stop"() {
        given:
        def response = new UmWarsawGenericResponse(
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
        def result = UmWarsawResponseMapper.mapBusLine("123", "02", response)

        then:
        result.stopId() == "123"
        result.stopNr() == "02"
        result.lines() ==~ ["250", "520"]
    }

}
