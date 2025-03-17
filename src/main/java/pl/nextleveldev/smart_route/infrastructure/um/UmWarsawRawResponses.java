package pl.nextleveldev.smart_route.infrastructure.um;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class UmWarsawRawResponses {

    public record Timetable(List<List<Value>> result) {
        record Value(String key, String value) {}
    }

    record BusLine(@JsonProperty("result") List<ResultValue> results) {
        public record ResultValue(List<ResultValue.Value> values) {
            public record Value(String key, String value) {}
        }
    }

    public record StopInfo(@JsonProperty("result") List<ResultValue> results) {
        public record ResultValue(List<ResultValue.Value> values) {
            public record Value(String key, String value) {}
        }
    }
}
