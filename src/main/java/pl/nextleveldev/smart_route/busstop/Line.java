package pl.nextleveldev.smart_route.busstop;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String lineIdentifier;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "bus_stop_line",
            joinColumns = @JoinColumn(name = "line_id"),
            inverseJoinColumns = @JoinColumn(name = "bus_stop_id")
    )
    private Set<BusStop> busStops = new HashSet<>();;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Line line)) return false;
        return Objects.equals(getLineIdentifier(), line.getLineIdentifier());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getLineIdentifier());
    }
}
