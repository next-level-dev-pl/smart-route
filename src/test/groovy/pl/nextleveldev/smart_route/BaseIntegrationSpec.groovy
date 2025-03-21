package pl.nextleveldev.smart_route

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import spock.lang.Specification

@AutoConfigureMockMvc
@SpringBootTest(properties = "spring.profiles.active=integration")
@Testcontainers
abstract class BaseIntegrationSpec extends Specification {

    public static final String TESTCONTAINER_DB_NAME = "testdb";
    public static final String TESTCONTAINER_DB_USERNAME = "testUsername";
    public static final String TESTCONTAINER_DB_PASSWORD = "testPassword";

    @Container
    public static final postgresSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgis/postgis:16-3.4")
            .asCompatibleSubstituteFor("postgres"))
            .withDatabaseName(TESTCONTAINER_DB_NAME)
            .withUsername(TESTCONTAINER_DB_USERNAME)
            .withPassword(TESTCONTAINER_DB_PASSWORD)
            .withReuse(true)

    static {
        postgresSQLContainer.start()
    }

    @DynamicPropertySource
    static def "setTestContainerProperties"(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresSQLContainer::getJdbcUrl)
        registry.add("spring.datasource.username", postgresSQLContainer::getUsername)
        registry.add("spring.datasource.password", postgresSQLContainer::getPassword)
    }

    @Autowired
    MockMvc mockMvc
}