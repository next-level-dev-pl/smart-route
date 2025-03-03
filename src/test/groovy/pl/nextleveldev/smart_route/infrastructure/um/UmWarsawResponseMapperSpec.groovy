package pl.nextleveldev.smart_route.infrastructure.um

import pl.nextleveldev.smart_route.infrastructure.um.api.KeyValue
import pl.nextleveldev.smart_route.infrastructure.um.api.ResultValues
import pl.nextleveldev.smart_route.infrastructure.um.api.UmWarsawGenericResponse
import spock.lang.Specification


class UmWarsawResponseMapperSpec extends Specification {

    def "map lines of buses for bus stop"() {
        given:
        def response = new UmWarsawGenericResponse(
                [
                        new ResultValues(
                                [
                                        new KeyValue("linia", "250"),
                                        new KeyValue("unknown", "250"),
                                ]
                        ),
                        new ResultValues(
                                [
                                        new KeyValue("linia", "520"),
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
