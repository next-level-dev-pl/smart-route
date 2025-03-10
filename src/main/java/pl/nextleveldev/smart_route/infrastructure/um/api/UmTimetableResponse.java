package pl.nextleveldev.smart_route.infrastructure.um.api;

import java.time.LocalTime;

public record UmTimetableResponse(String stopId, String stopNr, String line, Result result){
    record Result(String symbolOne, String symbolTwo, Integer brigade, String direction, String route, LocalTime time){}
}
