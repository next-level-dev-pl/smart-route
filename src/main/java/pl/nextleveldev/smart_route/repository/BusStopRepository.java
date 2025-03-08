package pl.nextleveldev.smart_route.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.nextleveldev.smart_route.busstop.entity.BusStop;

// fixme Treść javadoc zmienić na język angielski
@Repository
public interface BusStopRepository extends JpaRepository<BusStop, Long> {

    Optional<BusStop> findByStopIdName(String stopIdName);

    /**
     * Znajdź przystanki w podanym promieniu od współrzędnych geograficznych Wykorzystuje funkcje
     * przestrzenne PostGIS dla lepszej wydajności i dokładności
     *
     * @param latitude szerokość geograficzna
     * @param longitude długość geograficzna
     * @param radiusMeters promień w metrach
     * @return lista przystanków autobusowych w podanym promieniu
     */
    @Query(
            value =
                    "SELECT bs.* FROM bus_stops bs "
                            + "WHERE ST_DWithin("
                            + "bs.location, "
                            + "ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography, "
                            + ":radiusMeters"
                            + ")",
            nativeQuery = true)
    List<BusStop> findNearbyStops(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("radiusMeters") double radiusMeters);

    /**
     * Znajdź najbliższe przystanki od podanej lokalizacji, posortowane według odległości
     *
     * @param latitude szerokość geograficzna
     * @param longitude długość geograficzna
     * @param limit maksymalna liczba przystanków do zwrócenia
     * @return lista najbliższych przystanków autobusowych
     */
    @Query(
            value =
                    "SELECT bs.*, "
                            + "ST_Distance("
                            + "bs.location::geography, "
                            + "ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography"
                            + ") as distance "
                            + "FROM bus_stops bs "
                            + "ORDER BY distance "
                            + "LIMIT :limit",
            nativeQuery = true)
    List<BusStop> findNearestStops(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("limit") int limit);
}
