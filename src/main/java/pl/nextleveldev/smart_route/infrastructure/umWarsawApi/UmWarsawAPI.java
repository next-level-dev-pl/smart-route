package pl.nextleveldev.smart_route.infrastructure.umWarsawApi;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import pl.nextleveldev.smart_route.infrastructure.umWarsawApi.dto.StopDto;

@Validated
public interface UmWarsawAPI {

    String getSupportedBusLinesAtStop(@NotNull @Valid StopDto stopDto);
}
