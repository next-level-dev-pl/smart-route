package pl.nextleveldev.smart_route.route.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class RouteControllerITSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    def "should load spring context"() {
        when:
        def result = mockMvc.perform(get("/route/search")
                .param("start", "Centrum 02")
                .param("destination", "Prosta 01")
        )

        then:
        result.andExpect(status().isOk())
    }
}
