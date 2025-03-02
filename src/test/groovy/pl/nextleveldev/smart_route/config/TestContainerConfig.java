package pl.nextleveldev.smart_route;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class TestContainerConfig {

    private static final PostgreSQLContainer<?> POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:16.3")
            .withDatabaseName("testDb")
            .withUsername("testUsername")
            .withPassword("testPassword");

    static {
        POSTGRES_CONTAINER.start();
    }

    @Bean
    @DependsOn("POSTGRES_CONTAINER")
    public PostgreSQLContainer<?> postgresContainer() {
        return POSTGRES_CONTAINER;
    }

    @DynamicPropertySource
    static void configureTestContainerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
    }
}

