package pl.nextleveldev.smart_route.repository.busstop.BusStopRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nextleveldev.smart_route.busstop.entity.BusStop;

@Repository
public interface BusStopRepository extends JpaRepository<BusStop, Long> {

    Optional<BusStop> findByStopIdName(String stopIdName);
}
