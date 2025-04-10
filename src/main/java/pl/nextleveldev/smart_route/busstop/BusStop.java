package pl.nextleveldev.smart_route.busstop;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
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

    @Column(name = "stop_id", nullable = false) // field 'zespol' in UM API response
    private String stopId;

    @Column(name = "stop_nr", nullable = false) // field 'slupek' in UM API response
    private String stopNr;

    @Column(name = "stop_id_name", nullable = false) // field 'nazwa_zespolu' in UM API response
    private String stopIdName;

    @Column(name = "street_id", nullable = false) // field 'id_ulicy' in UM API response
    private String streetId;

    @Column(name = "location", nullable = false, columnDefinition = "geography(Point,4326)")
    // fields 'szer_geo' and 'dlug_geo' in UM API response
    @JdbcTypeCode(SqlTypes.GEOGRAPHY)
    private Point location;

    @Column(name = "direction", nullable = false) // field 'kierunek' in UM API response
    private String direction;

    @Column(name = "valid_from", nullable = false) // field 'obowiazuje_od' in UM API response
    private LocalDateTime validFrom;
}
