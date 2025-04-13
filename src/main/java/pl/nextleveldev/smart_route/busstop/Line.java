package pl.nextleveldev.smart_route.busstop;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.*;
import pl.nextleveldev.smart_route.busstop.joinTable.BusStopLine;

@Entity
@Table(name = "line")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String lineIdentifier;

    @Builder.Default
    @OneToMany(
            mappedBy = "line",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<BusStopLine> busStops = new HashSet<>();

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
