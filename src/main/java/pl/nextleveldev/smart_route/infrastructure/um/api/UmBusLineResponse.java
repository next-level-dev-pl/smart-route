package pl.nextleveldev.smart_route.infrastructure.um.api;

import java.util.List;

public record UmBusLineResponse(
        String stopId,
        String stopNr,
        List<String> lines
) {
}