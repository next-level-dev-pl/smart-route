package pl.nextleveldev.smart_route.infrastructure.um.api;

import java.util.List;

public record UmBusLineResponse(List<ResultValues> result) {
    public record ResultValues(List<Value> values) {}

    public record Value(String key, String value) {}
}
