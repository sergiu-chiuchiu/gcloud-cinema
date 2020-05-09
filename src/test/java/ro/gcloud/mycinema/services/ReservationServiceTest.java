package ro.gcloud.mycinema.services;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.gcloud.mycinema.entity.MovieInstance;
import ro.gcloud.mycinema.entity.Person;
import ro.gcloud.mycinema.entity.Reservation;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.repositories.ReservationRepository;
import ro.gcloud.mycinema.services.impl.ReservationServiceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ReservationServiceTest {

    @Mock
    ReservationRepository reservationRepository;

    @InjectMocks
    ReservationServiceImpl reservationService;

    Reservation reservation;
    List<Reservation> listOfReservations;
    Person person;
    MovieInstance movieInstance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        person = newPerson();
        movieInstance = newMovieInstance();

        reservation = new Reservation();
        reservation.setId(1L);
        reservation.setNumberOfTickets(3);
        reservation.setPerson(person);
        reservation.setMovieInstance(movieInstance);

        listOfReservations = new ArrayList<>();
        listOfReservations.add(reservation);

        person.setReservations(listOfReservations);
        movieInstance.setReservations(listOfReservations);
    }


    @Test
    public void shouldReturnAllReservations() throws NotFoundException {
        // arrange
        when(reservationRepository.findAll()).thenReturn(listOfReservations);

        //act
        Collection<Reservation> allReservations = reservationService.getAllReservations();

        //assert
        Assertions.assertThat(allReservations).isNotNull();
        Assertions.assertThat(allReservations.iterator().next()).isEqualToComparingFieldByFieldRecursively(reservation);
        Assertions.assertThat(allReservations.size()).isEqualTo(1);
    }

    @Test
    public void shouldSaveReservation() {

        when(reservationRepository.save(reservation)).thenReturn(reservation);

        Reservation savedReservation = reservationService.saveReservation(reservation);

        verify(reservationRepository, times(1)).save(reservation);
        Assertions.assertThat(savedReservation).isNotNull();
        Assertions.assertThat(savedReservation).isEqualToComparingFieldByFieldRecursively(reservation);
    }

    @Test
    public void shouldReturnReservationById() throws NotFoundException {
        // arrange
        when(reservationRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(reservation));

        //act
        Reservation reservationById = reservationService.getReservationById(1L);

        //assert
        Assertions.assertThat(reservationById).isNotNull();
        Assertions.assertThat(reservationById).isEqualToComparingFieldByFieldRecursively(reservation);

    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundExceptionWhenReservationById() throws NotFoundException {
        // arrange
        when(reservationRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        //act
        Reservation reservationById = reservationService.getReservationById(2L);

        //assert
        fail();
    }


    @Test
    public void shouldDeleteReservation() {

        when(reservationRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(reservation));

        reservationService.deleteReservation(reservation);

        Assertions.assertThat(reservation.getPerson()).isNull();
        Assertions.assertThat(reservation.getMovieInstance()).isNull();
        Assertions.assertThat(person.getReservations()).isEmpty();
        Assertions.assertThat(movieInstance.getReservations()).isEmpty();
        verify(reservationRepository, times(1)).delete(reservation);
    }

    private Person newPerson() {
        Person person = new Person();
        person.setId(1L);
        person.setFullName("Andrei B");
        person.setEmail("andrei@gmail.com");
        person.setPhone("0744456678");
        return person;
    }

    private MovieInstance newMovieInstance() {
        MovieInstance movieInstance = new MovieInstance();
        movieInstance.setId(1L);
        movieInstance.setStartDate(new Date());
        movieInstance.setEndDate(new Date());
        movieInstance.setAvailableSeats(122);
        return movieInstance;
    }

}
