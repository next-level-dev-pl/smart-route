package pl.nextleveldev.smart_route.route.api

import pl.nextleveldev.smart_route.BaseIntegrationSpec

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class RouteControllerITSpec extends BaseIntegrationSpec {

    def "should load spring context"() {
        when:
        def result = mockMvc.perform(get("/routes")
                .param("start", "Centrum 02")
                .param("destination", "Prosta 01")
        )

        then:
        result.andExpect(status().isOk())
    }

}
