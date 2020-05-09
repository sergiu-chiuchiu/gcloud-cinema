package ro.gcloud.mycinema.controllers;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.gcloud.mycinema.dto.PersonDto;
import ro.gcloud.mycinema.entity.Person;
import ro.gcloud.mycinema.exceptions.BadRequestException;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.services.impl.PersonServiceImpl;

import java.net.URISyntaxException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class PersonControllerTest {


    @Mock
    private PersonServiceImpl personService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    PersonController personController;

    PersonDto personDto = new PersonDto();
    Person person = new Person();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnResponseEntityForSave() throws URISyntaxException {

        // arrange
        createObjects(person, personDto);
        Mockito.when(personService.savePerson(person)).thenReturn(person);
        Mockito.when(modelMapper.map(personDto, Person.class)).thenReturn(person);

        // act
        ResponseEntity<Person> personResponseEntity = personController.savePerson(personDto);

        // assert
        Assertions.assertThat(personResponseEntity).isNotNull();
        Assertions.assertThat(personResponseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(personResponseEntity.getBody()).isEqualToComparingFieldByFieldRecursively(person);
        Assertions.assertThat(personResponseEntity.getHeaders().getLocation().toString()).isEqualTo("/api/persons/1");
    }

    @Test
    public void shouldReturnUpdatedPerson() throws BadRequestException, NotFoundException, URISyntaxException {
        createObjects(person, personDto);

        // arrange
        Mockito.when(personService.getPersonById(1L)).thenReturn(person);
        Mockito.when(personService.updatePerson(person)).thenReturn(person);

        // act
        Person personResponse = personController.updatePerson(1L, personDto);

        // assert
        verify(personService, times(1)).updatePerson(person);
        Assertions.assertThat(personResponse).isNotNull();
        Assertions.assertThat(personResponse).isEqualToComparingFieldByFieldRecursively(person);

    }

    @Test
    public void shouldDeletePerson() throws NotFoundException {

        // arrange
        createObjects(person, personDto);
        when(personService.getPersonById(1L)).thenReturn(person);

        // act
        personController.deletePerson(1L);

        // assert
        verify(personService, times(1)).deletePerson(person);
    }

    private void createObjects(Person person, PersonDto personDto) {
        personDto.setId(1L);
        personDto.setFullName("Andrei B");
        personDto.setEmail("andrei@gmail.com");
        personDto.setPhone("0744456678");

        person.setId(1L);
        person.setFullName("Andrei Bdto");
        person.setEmail("andrei@gmail.comdto");
        person.setPhone("0744456678dto");
    }

    @After
    public void tearDown() {
        person = null;
        personDto = null;
    }

}
