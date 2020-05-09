package ro.gcloud.mycinema.services;

import ro.gcloud.mycinema.entity.Person;
import ro.gcloud.mycinema.exceptions.NotFoundException;

import java.util.Collection;

public interface PersonService {
    Person savePerson(Person person);
    Collection<Person> getAllPersons();
    Person getPersonById(Long id) throws NotFoundException;
    Person updatePerson(Person personToUpdate);

    void deletePersonById(Long id);

    void deletePerson(Person person);

    Boolean personExists(Long id);
}
