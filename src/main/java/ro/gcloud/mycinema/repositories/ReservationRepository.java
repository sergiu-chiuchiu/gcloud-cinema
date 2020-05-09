package ro.gcloud.mycinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.gcloud.mycinema.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {


}
