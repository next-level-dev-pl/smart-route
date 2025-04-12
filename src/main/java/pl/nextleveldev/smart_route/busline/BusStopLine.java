package pl.nextleveldev.smart_route.busline;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.nextleveldev.smart_route.busstop.BusStop;

@Entity
@Table(name = "bus_stop_line")
@Getter
@Setter
public class BusStopLine {

    @EmbeddedId private BusStopLineId id;

    @ManyToOne
    @MapsId("lineId")
    @JoinColumn(name = "line_id")
    private BusLine busLine;

    @ManyToOne
    @MapsId("busStopId")
    @JoinColumn(name = "bus_stop_id")
    private BusStop busStop;

    public BusStopLine() {}

    public BusStopLine(BusLine busLine, BusStop busStop) {
        this.busLine = busLine;
        this.busStop = busStop;
        this.id = new BusStopLineId(busLine.getId(), busStop.getId());
    }
}
