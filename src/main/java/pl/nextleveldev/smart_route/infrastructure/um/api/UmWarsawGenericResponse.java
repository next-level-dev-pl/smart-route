package pl.nextleveldev.smart_route.infrastructure.um.api;

import java.util.List;

public record UmWarsawGenericResponse(List<ResultValue> results) {
    public record ResultValue(List<Value> values) {
        public record Value(String key, String value) {}
    }
}
