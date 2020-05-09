package ro.gcloud.mycinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.gcloud.mycinema.entity.Cinema;

import java.util.List;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    @Query("SELECT c FROM Cinema c JOIN c.movieRooms WHERE capacity = :capacity")
    List<Cinema> getCinemaByMovieRoomsCapacity(@Param("capacity") Integer capacity);

}
