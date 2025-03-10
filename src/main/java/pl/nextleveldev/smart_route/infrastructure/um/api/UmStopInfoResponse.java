package pl.nextleveldev.smart_route.infrastructure.um.api;

import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

public record UmStopInfoResponse(String stopId, String stopNr, String stopIdName, Point location, String direction, LocalDateTime validFrom) {
}
