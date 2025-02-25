package pl.nextleveldev.smart_route.infrastructure.um.api;

import java.util.List;

public record UmBusLineResponse(
        String stopId,
        String stopNr,
        List<String> lines
) {

    @Override
    public String toString() {
        return "UmBusLineResponse{\n" +
                "stopId=" + stopId + '\n' +
                "stopNr=" + stopNr + '\n' +
                "lines=" + lines + '\n' +
                '}';
    }
}