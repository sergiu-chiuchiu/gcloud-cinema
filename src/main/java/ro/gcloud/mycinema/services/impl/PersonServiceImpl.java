package ro.gcloud.mycinema.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.gcloud.mycinema.entity.Person;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.repositories.PersonRepository;
import ro.gcloud.mycinema.services.PersonService;

import java.util.Collection;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;
    private Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public Person savePerson(Person personToSave) {
        logger.info("Attempting to persist in DB Customer: " + personToSave.getFullName());
        return personRepository.save(personToSave);
    }

    @Override
    public Collection<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public Person getPersonById(Long id) throws NotFoundException {
        logger.info("Retrieving the Customer with id=" + id);
        return personRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Person with id=%s was not found.", id)));
    }
    @Override
    public Person updatePerson(Person personToUpdate) {
        return personRepository.save(personToUpdate);
    }

    @Override
    public void deletePersonById(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public void deletePerson(Person person) {
        personRepository.delete(person);
    }

    @Override
    public Boolean personExists(Long id) {
        return personRepository.existsById(id);
    }

}
