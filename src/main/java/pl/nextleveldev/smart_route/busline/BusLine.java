package pl.nextleveldev.smart_route.busline;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.nextleveldev.smart_route.busstop.BusStop;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BusLine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String lineIdentifier;

    @ManyToOne
    @JoinColumn(name = "bus_stop_id")
    private BusStop stop;

    BusLine(String lineIdentifier, BusStop stop) {
        this.lineIdentifier = lineIdentifier;
        this.stop = stop;
    }

}
