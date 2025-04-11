package pl.nextleveldev.smart_route.busline.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.nextleveldev.smart_route.busstop.BusStop;

import java.util.UUID;

@Entity
@Getter
@Setter
public class BusLine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String lineIdentifier;

    @ManyToOne
    @JoinColumn(name = "bus_stop_id")
    private BusStop stop;
}
