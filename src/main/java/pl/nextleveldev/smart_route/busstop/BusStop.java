package pl.nextleveldev.smart_route.busstop;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.locationtech.jts.geom.Point;
import pl.nextleveldev.smart_route.busline.BusLine;

@Entity
@Table(name = "bus_stops")
@Builder
@Getter
@Setter
@EqualsAndHashCode // Worse performance due to the presence of the `location` type
// 'SqlTypes.GEOMETRY' field. However, it is necessary.
@ToString(exclude = "location")
@NoArgsConstructor
@AllArgsConstructor
public class BusStop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "stop_id") // field 'zespol' in UM API response
    private String stopId;

    @Column(name = "stop_nr") // field 'slupek' in UM API response
    private String stopNr;

    @Column(name = "stop_id_name") // field 'nazwa_zespolu' in UM API response
    private String stopIdName;

    @Column(name = "street_id") // field 'id_ulicy' in UM API response
    private String streetId;

    @Column(name = "location", columnDefinition = "geography(Point,4326)")
    // fields 'szer_geo' and 'dlug_geo' in UM API response
    @JdbcTypeCode(SqlTypes.GEOGRAPHY)
    private Point location;

    @Column(name = "direction") // field 'kierunek' in UM API response
    private String direction;

    @Column(name = "valid_from") // field 'obowiazuje_od' in UM API response
    private LocalDateTime validFrom;

    @OneToMany(mappedBy = "stop")
    private Set<BusLine> lines;

    public BusStop(
            String stopId,
            String stopNr,
            String stopIdName,
            String streetId,
            Point location,
            String direction,
            LocalDateTime validFrom) {
        this.stopId = stopId;
        this.stopNr = stopNr;
        this.stopIdName = stopIdName;
        this.streetId = streetId;
        this.location = location;
        this.direction = direction;
        this.validFrom = validFrom;
    }
}
