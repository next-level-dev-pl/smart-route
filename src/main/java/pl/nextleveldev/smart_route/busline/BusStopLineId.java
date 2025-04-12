package pl.nextleveldev.smart_route.busline;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class BusStopLineId implements Serializable {

    @Column(name = "line_id")
    private UUID lineId;

    @Column(name = "bus_stop_id")
    private UUID busStopId;

    public BusStopLineId() {}

    public BusStopLineId(UUID lineId, UUID busStopId) {
        this.lineId = lineId;
        this.busStopId = busStopId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusStopLineId that)) return false;
        return Objects.equals(lineId, that.lineId) && Objects.equals(busStopId, that.busStopId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineId, busStopId);
    }
}
