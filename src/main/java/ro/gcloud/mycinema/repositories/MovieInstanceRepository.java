package ro.gcloud.mycinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.gcloud.mycinema.entity.MovieInstance;

import java.util.Date;
import java.util.List;

@Repository
public interface MovieInstanceRepository extends JpaRepository<MovieInstance, Long> {

    @Query(
            value = "SELECT * FROM movie_instance mi WHERE mi.start_date = :date",
            nativeQuery = true)
    List<MovieInstance> findAllMovieInstances (@Param("date") Date startDate);
}
