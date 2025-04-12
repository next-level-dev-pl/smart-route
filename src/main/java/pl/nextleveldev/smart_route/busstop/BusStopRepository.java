package pl.nextleveldev.smart_route.busstop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface BusStopRepository extends JpaRepository<BusStop, Long> {
}

@Repository
interface BusLineRepository extends JpaRepository<Line, UUID> {

    Optional<Line> findByLineIdentifier(String lineIdentifier);
}
