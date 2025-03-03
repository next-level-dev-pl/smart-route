package pl.nextleveldev.smart_route.infrastructure.um.api;

import java.util.List;

public record UmTimetableResponse(List<List<KeyValue>> result) {}
