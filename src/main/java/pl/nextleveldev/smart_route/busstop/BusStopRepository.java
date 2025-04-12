package pl.nextleveldev.smart_route.busstop;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BusStopRepository extends JpaRepository<BusStop, Long> {

    @EntityGraph(attributePaths = {"lines"})
    Optional<BusStop> findById(UUID uuid);
}

@Repository
interface BusLineRepository extends JpaRepository<Line, UUID> {

    Optional<Line> findByLineIdentifier(String lineIdentifier);
}
