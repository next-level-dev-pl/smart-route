package pl.nextleveldev.smart_route.busline;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nextleveldev.smart_route.busline.entity.BusLine;

import java.util.UUID;

@Repository
public interface BusLineRepository extends JpaRepository<BusLine, UUID> {
}
