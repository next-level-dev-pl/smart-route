package pl.nextleveldev.smart_route.infrastructure.um.api;

import java.time.LocalTime;
import java.util.List;

public record UmTimetableResponse(String stopId, String stopNr, String line, List<Result> result){
    public record Result(String symbolOne, String symbolTwo, Integer brigade, String direction, String route, LocalTime time){}
}
