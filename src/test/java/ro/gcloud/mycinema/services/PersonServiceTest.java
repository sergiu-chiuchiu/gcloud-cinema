package ro.gcloud.mycinema.services;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.gcloud.mycinema.entity.Person;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.repositories.PersonRepository;
import ro.gcloud.mycinema.services.impl.PersonServiceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class PersonServiceTest {


    @Mock
    PersonRepository personRepository;

    @InjectMocks
    PersonServiceImpl personService;

    Person person;
    List<Person> listOfPersons;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        person = new Person();
        person.setId(1L);
        person.setFullName("Andrei B");
        person.setEmail("andrei@gmail.com");
        person.setPhone("0744456678");

        listOfPersons = new ArrayList<>();
        listOfPersons.add(person);
    }

    @Test
    public void shouldReturnPersonById() throws NotFoundException {
        // arrange
        when(personRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(person));


        //act
        Person personById = personService.getPersonById(1L);

        //assert
        Assertions.assertThat(personById).isNotNull();
        Assertions.assertThat(personById).isEqualToComparingFieldByFieldRecursively(person);

    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundExceptionWhenPersonById() throws NotFoundException {
        // arrange
        when(personRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        //act
        Person personById = personService.getPersonById(2L);

        //assert
        fail();
    }


    @Test
    public void shouldReturnAllPersons() throws NotFoundException {
        // arrange
        when(personRepository.findAll()).thenReturn(listOfPersons);

        //act
        Collection<Person> allPersons = personService.getAllPersons();

        //assert
        Assertions.assertThat(allPersons).isNotNull();
        Assertions.assertThat(allPersons.iterator().next()).isEqualToComparingFieldByFieldRecursively(person);
        Assertions.assertThat(allPersons.size()).isEqualTo(1);
    }

    @Test
    public void shouldSavePerson()  {

        when(personRepository.save(person)).thenReturn(person);

        Person savedPerson = personService.savePerson(person);

        verify(personRepository, times(1)).save(person);
        Assertions.assertThat(savedPerson).isNotNull();
        Assertions.assertThat(savedPerson).isEqualToComparingFieldByFieldRecursively(person);
    }

    @Test
    public void shouldDeletePersonById()  {

        when(personRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(person));

        personService.deletePersonById(1L);

        verify(personRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldDeletePerson()  {

        when(personRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(person));

        personService.deletePerson(person);

        verify(personRepository, times(1)).delete(person);
    }


    @Test
    public void shouldReturnIfPersonExists() throws NotFoundException {
        // arrange
        when(personRepository.existsById(1L)).thenReturn(true);


        //act
        Boolean personExistsById = personService.personExists(1L);

        //assert
        Assertions.assertThat(personExistsById).isTrue();
        verify(personRepository, times(1)).existsById(1L);

    }

}
