package pl.nextleveldev.smart_route

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

@AutoConfigureMockMvc
@SpringBootTest(properties = "spring.profiles.active=integration")
abstract class BaseIntegrationSpec extends Specification {

    @Autowired
    MockMvc mockMvc

}