package ro.gcloud.mycinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.gcloud.mycinema.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
