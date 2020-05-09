package ro.gcloud.mycinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.gcloud.mycinema.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
