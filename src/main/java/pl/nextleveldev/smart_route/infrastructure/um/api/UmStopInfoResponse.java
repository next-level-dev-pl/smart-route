package pl.nextleveldev.smart_route.infrastructure.um.api;

import java.util.List;

public record UmStopInfoResponse(List<ResultValues> result) {
    public record ResultValues(List<Value> values) {}

    public record Value(String key, String value) {}
}
