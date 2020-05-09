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
import ro.gcloud.mycinema.dto.ReservationDto;
import ro.gcloud.mycinema.entity.Reservation;
import ro.gcloud.mycinema.exceptions.BadRequestException;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.services.impl.ReservationServiceImpl;

import java.net.URISyntaxException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ReservationControllerTest {


    @Mock
    private ReservationServiceImpl reservationService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    ReservationController reservationController;

    ReservationDto reservationDto = new ReservationDto();
    Reservation reservation = new Reservation();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnResponseEntityForSave() throws URISyntaxException {

        // arrange
        createObjects(reservation, reservationDto);
        Mockito.when(reservationService.saveReservation(reservation)).thenReturn(reservation);
        Mockito.when(modelMapper.map(reservationDto, Reservation.class)).thenReturn(reservation);

        // act
        ResponseEntity<Reservation> reservationResponseEntity = reservationController.saveReservation(reservationDto);

        // assert
        Assertions.assertThat(reservationResponseEntity).isNotNull();
        Assertions.assertThat(reservationResponseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(reservationResponseEntity.getBody()).isEqualToComparingFieldByFieldRecursively(reservation);
        Assertions.assertThat(reservationResponseEntity.getHeaders().getLocation().toString()).isEqualTo("/api/reservations/1");
    }

    @Test
    public void shouldReturnUpdatedReservation() throws BadRequestException, NotFoundException, URISyntaxException {
        createObjects(reservation, reservationDto);

        // arrange
        Mockito.when(reservationService.getReservationById(1L)).thenReturn(reservation);
        Mockito.when(reservationService.saveReservation(reservation)).thenReturn(reservation);

        // act
        ResponseEntity reservationResponseEntity = reservationController.updateReservation(1L, reservationDto);

        // assert
        verify(reservationService, times(1)).saveReservation(reservation);
        Assertions.assertThat(reservationResponseEntity).isNotNull();
        Assertions.assertThat(reservationResponseEntity).isEqualToComparingFieldByFieldRecursively(reservation);

    }

    @Test
    public void shouldDeleteReservation() throws NotFoundException {

        // arrange
        createObjects(reservation, reservationDto);
        when(reservationService.getReservationById(1L)).thenReturn(reservation);

        // act
        reservationController.deleteReservation(1L);

        // assert
        verify(reservationService, times(1)).deleteReservation(reservation);
    }

    private void createObjects(Reservation reservation, ReservationDto reservationDto) {
        reservationDto.setId(1L);
        reservationDto.setNumberOfTickets(3);

        reservation.setId(1L);
        reservation.setNumberOfTickets(23);
    }

    @After
    public void tearDown() {
        reservation = null;
        reservationDto = null;
    }


}

