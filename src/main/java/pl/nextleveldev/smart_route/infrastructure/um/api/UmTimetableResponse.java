package pl.nextleveldev.smart_route.infrastructure.um.api;

import java.time.LocalTime;

public record UmTimetableResponse(String symbolOne, String symbolTwo, Integer brigade, String direction, String route, LocalTime time){
}
