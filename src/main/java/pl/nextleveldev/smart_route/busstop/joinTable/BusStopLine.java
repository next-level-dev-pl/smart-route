package pl.nextleveldev.smart_route.busstop.joinTable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.nextleveldev.smart_route.busstop.BusStop;
import pl.nextleveldev.smart_route.busstop.Line;

import java.util.Objects;

@Entity
@Table(name = "bus_stop_line")
@Getter
@Setter
public class BusStopLine {

    @EmbeddedId private BusStopLineId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("lineId")
    @JoinColumn(name = "line_id")
    private Line line;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("busStopId")
    @JoinColumn(name = "bus_stop_id")
    private BusStop busStop;

    public BusStopLine() {}

    public BusStopLine(Line line, BusStop busStop) {
        this.line = line;
        this.busStop = busStop;
        this.id = new BusStopLineId(line.getId(), busStop.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BusStopLine that)) return false;
        return Objects.equals(getLine(), that.getLine()) && Objects.equals(getBusStop(), that.getBusStop());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLine(), getBusStop());
    }
}
