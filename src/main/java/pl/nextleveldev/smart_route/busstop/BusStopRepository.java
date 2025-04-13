package pl.nextleveldev.smart_route.busstop;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nextleveldev.smart_route.busstop.joinTable.BusStopLine;
import pl.nextleveldev.smart_route.busstop.joinTable.BusStopLineId;

@Repository
interface BusStopRepository extends JpaRepository<BusStop, Long> {

    @EntityGraph(attributePaths = {"lines"})
    Optional<BusStop> findById(UUID uuid);
}

@Repository
interface LineRepository extends JpaRepository<Line, UUID> {

    Optional<Line> findByLineIdentifier(String lineId);
}

@Repository
interface BusStopLineRepository extends JpaRepository<BusStopLine, BusStopLineId> {
    void deleteAllByBusStop(BusStop busStop);
}
