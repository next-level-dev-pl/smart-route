package pl.nextleveldev.smart_route.model.joinTable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.nextleveldev.smart_route.busstop.BusStop;
import pl.nextleveldev.smart_route.busstop.Line;
import pl.nextleveldev.smart_route.model.compositePK.BusStopLineId;

@Entity
@Table(name = "bus_stop_line")
@Getter
@Setter
public class BusStopLine {

    @EmbeddedId
    private BusStopLineId id;

    @ManyToOne
    @MapsId("lineId")
    @JoinColumn(name = "line_id")
    private Line line;

    @ManyToOne
    @MapsId("busStopId")
    @JoinColumn(name = "bus_stop_id")
    private BusStop busStop;

    public BusStopLine() {}

    public BusStopLine(Line line, BusStop busStop) {
        this.line = line;
        this.busStop = busStop;
        this.id = new BusStopLineId(line.getId(), busStop.getId());
    }
}
