package se.kth.journalsystem.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.kth.journalsystem.patient.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}


