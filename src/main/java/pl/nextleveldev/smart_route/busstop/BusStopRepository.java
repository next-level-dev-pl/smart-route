package pl.nextleveldev.smart_route.busstop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusStopRepository extends JpaRepository<BusStop, Long> {

    Optional<BusStop> findByStopIdName(String stopIdName);
}
