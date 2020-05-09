package ro.gcloud.mycinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.gcloud.mycinema.entity.MovieRoom;

import java.util.List;

@Repository
public interface MovieRoomRepository extends JpaRepository<MovieRoom, Long> {
    List<MovieRoom>getMovieRoomByCinema_Id(Long cinemaId);

}
