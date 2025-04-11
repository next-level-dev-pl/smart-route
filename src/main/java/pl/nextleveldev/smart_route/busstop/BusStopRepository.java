package pl.nextleveldev.smart_route.busstop;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusStopRepository extends JpaRepository<BusStop, Long> {

    @EntityGraph(attributePaths = {"lines"})
    Optional<BusStop> findByStopIdName(String stopIdName);
}
