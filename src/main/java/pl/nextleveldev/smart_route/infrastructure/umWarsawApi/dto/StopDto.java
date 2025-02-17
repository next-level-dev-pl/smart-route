package pl.nextleveldev.smart_route.infrastructure.umWarsawApi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StopDto(
        @NotNull @NotBlank  String id,
        @NotNull @NotBlank String nr
) {
}
