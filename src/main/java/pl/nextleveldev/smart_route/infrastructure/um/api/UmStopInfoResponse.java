package pl.nextleveldev.smart_route.infrastructure.um.api;

import java.time.LocalDateTime;
import org.locationtech.jts.geom.Point;

public record UmStopInfoResponse(
        String stopId,
        String stopNr,
        String stopIdName,
        Integer streetId,
        Point location,
        String direction,
        LocalDateTime validFrom) {

    @Override
    public String toString() {
        return "UmStopInfoResponse{"
                + "stopId='"
                + stopId
                + '\''
                + ", stopNr='"
                + stopNr
                + '\''
                + ", stopIdName='"
                + stopIdName
                + '\''
                + ", streetId="
                + streetId
                + ", location="
                + location
                + ", direction='"
                + direction
                + '\''
                + ", validFrom="
                + validFrom
                + '}';
    }
}
