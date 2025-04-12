package pl.nextleveldev.smart_route.busline;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import pl.nextleveldev.smart_route.busstop.BusStop;

@Entity
@Getter
@Setter
public class BusLine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String lineIdentifier;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "bus_stop_line",
            joinColumns = @JoinColumn(name = "line_id"),
            inverseJoinColumns = @JoinColumn(name = "bus_stop_id"))
    private Set<BusStop> busStops = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BusLine busLine)) return false;
        return Objects.equals(getLineIdentifier(), busLine.getLineIdentifier());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getLineIdentifier());
    }
}
