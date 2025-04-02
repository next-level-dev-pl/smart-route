package pl.nextleveldev.smart_route.infrastructure.um;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

class UmWarsawRawResponses {

    record Timetable(List<List<Value>> result) {
        record Value(String key, String value) {}
    }

    record BusLine(@JsonProperty("result") List<ResultValue> results) {
        record ResultValue(List<ResultValue.Value> values) {
            record Value(String key, String value) {}
        }
    }

    record StopInfo(@JsonProperty("result") List<ResultValue> results) {
        record ResultValue(List<ResultValue.Value> values) {
            public record Value(String key, String value) {}
        }
    }
}
