package pl.nextleveldev.smart_route.busline;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface BusLineRepository extends JpaRepository<BusLine, UUID> {

    Optional<BusLine> findByLineIdentifier(String lineIdentifier);
}
