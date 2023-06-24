package nulp.trspo.flight.persistence.repository;

import nulp.trspo.flight.persistence.entity.PlaneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaneRepository extends JpaRepository<PlaneEntity, Long> {
}
