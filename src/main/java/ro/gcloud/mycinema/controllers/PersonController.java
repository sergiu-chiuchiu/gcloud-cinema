package ro.gcloud.mycinema.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.gcloud.mycinema.dto.PersonDto;
import ro.gcloud.mycinema.entity.Person;
import ro.gcloud.mycinema.exceptions.BadRequestException;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.services.PersonService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    private final PersonService personService;
    private final ModelMapper modelMapper;

    @Autowired
    public PersonController(PersonService personService, ModelMapper modelMapper) {
        this.personService = personService;
        this.modelMapper = modelMapper;
    }

    private Logger logger = LogManager.getLogger(this.getClass());


    @PostMapping
    public ResponseEntity<Person> savePerson(@RequestBody PersonDto personToSave) throws URISyntaxException {
        Person person = personService.savePerson(modelMapper.map(personToSave, Person.class));
        return ResponseEntity.created(new URI("/api/persons/" + person.getId())).body(person);
    }

    @GetMapping(value = "/{id}")
    public Person getPersonById(@PathVariable("id") Long id) throws NotFoundException {
        logger.info("Requested Person with id=" + id);
        return personService.getPersonById(id);
    }

    @GetMapping
    public Collection<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable("id") Long id, @RequestBody PersonDto updatedPerson) throws BadRequestException, NotFoundException {
        if (!id.equals(updatedPerson.getId())) {
            logger.warn("The ids do not match: received id=" + id + " in path and id=" + updatedPerson.getId() + " in entity!");
            throw new BadRequestException("Different ids: " + id + " from PathVariable and " + updatedPerson.getId() + " from RequestBody");
        }
        Person personDb = personService.getPersonById(id);
        modelMapper.map(updatedPerson, personDb);
        return personService.updatePerson(personDb);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable("id") Long id) throws NotFoundException {
        Person person = personService.getPersonById(id);
        personService.deletePerson(person);
    }

}
