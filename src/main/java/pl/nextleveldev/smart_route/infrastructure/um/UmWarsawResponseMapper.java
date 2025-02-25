package pl.nextleveldev.smart_route.infrastructure.um;

import org.springframework.stereotype.Component;
import pl.nextleveldev.smart_route.infrastructure.um.UmWarsawClient.BeforeParseUmBusLineResponse;
import pl.nextleveldev.smart_route.infrastructure.um.api.UmBusLineResponse;

import java.util.ArrayList;
import java.util.List;

@Component
class UmWarsawResponseMapper {

    UmBusLineResponse mapBusLine(String stopId, String stopNr, BeforeParseUmBusLineResponse beforeParseUmBusLineResponse){
        List<String> lines = new ArrayList<>();
        beforeParseUmBusLineResponse.result().stream()
                .map(BeforeParseUmBusLineResponse.ResultValues::values)
                .forEach(value -> lines.add(value.getFirst().value()));

        return new UmBusLineResponse(stopId, stopNr, lines);
    }
}
