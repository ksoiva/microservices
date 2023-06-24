package nulp.trspo.ticket.persistence.repository;

import nulp.trspo.ticket.persistence.entity.PassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<PassengerEntity, Long> {
}
