package pl.nextleveldev.smart_route

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

@SpringBootTest
class ApplicationContextLoadTest extends Specification {

    @Autowired
    ApplicationContext context

    @Value('${spring.application.name}')
    private String appName

    def "should load spring context"() {
        expect:
        context != null
        context.id.contains(appName)
    }
}
