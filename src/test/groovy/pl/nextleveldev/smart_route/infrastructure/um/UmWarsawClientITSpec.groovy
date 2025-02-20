package pl.nextleveldev.smart_route.infrastructure.um


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(properties = "spring.profiles.active=integration")
class UmWarsawClientITSpec extends Specification {

    @Autowired
    UmWarsawClient umWarsawAPI

//    def "response from api should have data"() {
//        given:
//        def busstopId = "7009"
//        def busstopNr = "01"
//        def line = "520"
//
//        when:
//        def result = umWarsawAPI.getTimetableFor(busstopId, busstopNr, line)
//
//        then:
//        !result.result().empty
//    }
//
//    def "for invalid input result is empty"() {
//        given:
//        def busstopId = "7009"
//        def busstopNr = "01"
//        def line = "001"
//
//        when:
//        def result = umWarsawAPI.getTimetableFor(busstopId, busstopNr, line)
//
//        then:
//        !result.result()
//    }

}