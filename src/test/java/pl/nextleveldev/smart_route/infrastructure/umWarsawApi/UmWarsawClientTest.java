package pl.nextleveldev.smart_route.infrastructure.umWarsawApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = {UmWarsawConfig.class} )
@TestPropertySource(properties = {
        "um.warsaw.api-key=${UM_WARSAW_API_KEY}",
        "um.warsaw.base-url=https://api.um.warszawa.pl",
        "um.warsaw.timetable-resource-url=api/action/dbtimetable_get",
        "um.warsaw.timetable-resource-id=e923fa0e-d96c-43f9-ae6e-60518c9f3238",
        "um.warsaw.bus-line-on-stop-resource-id=88cd555f-6f31-43ca-9de4-66c479ad5942"
})
class UmWarsawClientTest {

    @Autowired
    UmWarsawAPI umWarsawAPI;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldNotThrowException_whenParamsAreValid() {
        //given
        var busstopId = "7009";
        var busstopNr = "01";

        //when
        Exception result = catchException(() -> umWarsawAPI.getSupportedBusLinesAtStop(busstopId, busstopNr));

        //then
        assertThat(result).isNull();
    }

    @Test
    void shouldReturnJsonValue_whenParamsAreValid() {
        //given
        var busstopId = "7009";
        var busstopNr = "01";

        //when
        String result = umWarsawAPI.getSupportedBusLinesAtStop(busstopId, busstopNr);

        //then
        assertTrue(isValidJson(result));
    }

    private boolean isValidJson(String json) {
        try {
            objectMapper.readTree(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}