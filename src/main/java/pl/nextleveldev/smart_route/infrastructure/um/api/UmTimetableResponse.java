package pl.nextleveldev.smart_route.infrastructure.um.api;

import java.time.LocalTime;
import java.util.List;

public record UmTimetableResponse(String stopId, String stopNr, String line, List<Result> result) {
    public record Result(
            String symbolOne,
            String symbolTwo,
            Integer brigade,
            String direction,
            String route,
            LocalTime time) {
        @Override
        public String toString() {
            return "Result{"
                    + "symbolOne='"
                    + symbolOne
                    + '\''
                    + ", symbolTwo='"
                    + symbolTwo
                    + '\''
                    + ", brigade="
                    + brigade
                    + ", direction='"
                    + direction
                    + '\''
                    + ", route='"
                    + route
                    + '\''
                    + ", time="
                    + time
                    + '}';
        }
    }

    @Override
    public String toString() {
        return "UmTimetableResponse{"
                + "stopId='"
                + stopId
                + '\''
                + ", stopNr='"
                + stopNr
                + '\''
                + ", line='"
                + line
                + '\''
                + ", result="
                + result
                + '}';
    }
}
