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
import ro.gcloud.mycinema.dto.CinemaDto;
import ro.gcloud.mycinema.entity.Cinema;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.services.impl.CinemaServiceImpl;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class CinemaControllerTest {

    @Mock
    private CinemaServiceImpl cinemaService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    CinemaController cinemaController;

    CinemaDto cinemaDto = new CinemaDto();
    Cinema cinema = new Cinema();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnResponseEntityForSave() throws URISyntaxException {
        createObjects(cinema, cinemaDto);

        // arrange
        Mockito.when(cinemaService.saveCinema(cinema)).thenReturn(cinema);
        Mockito.when(modelMapper.map(cinemaDto, Cinema.class)).thenReturn(cinema);

        // act
        ResponseEntity<Cinema> cinemaResponseEntity = cinemaController.saveCinema(cinemaDto);

        // assert
        Assertions.assertThat(cinemaResponseEntity).isNotNull();
        Assertions.assertThat(cinemaResponseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(cinemaResponseEntity.getBody().getAddress()).isEqualTo(cinema.getAddress());

        Assertions.assertThat(cinemaResponseEntity.getBody()).isEqualToComparingFieldByFieldRecursively(cinema);
        Assertions.assertThat(cinemaResponseEntity.getHeaders().getLocation().toString()).isEqualTo("/api/cinemas/1");

    }

    @Test
    public void shouldReturnListOfCinemasByMovieRoomCapacity() {
        createObjects(cinema, cinemaDto);
        List<Cinema> cinemaList = new ArrayList<>();
        cinemaList.add(cinema);

        // arrange
        Mockito.when(cinemaService.getCinemaByMovieRoomsCapacity(200)).thenReturn(cinemaList);

        // act
        List<Cinema> cinemaListResponse = cinemaController.getCinemaByMovieRoomCapacity(200);

        // assert

        verify(cinemaService, times(1)).getCinemaByMovieRoomsCapacity(200);
        Assertions.assertThat(cinemaListResponse).isNotNull();
        Assertions.assertThat(cinemaListResponse.get(0)).isEqualToComparingFieldByFieldRecursively(cinema);
    }

    @Test
    public void shouldDeleteCinema() throws NotFoundException {
        createObjects(cinema, cinemaDto);

        // arrange
        when(cinemaService.getCinemaById(1L)).thenReturn(cinema);

        // act
        cinemaController.deleteCinema(1L);

        // assert
        verify(cinemaService, times(1)).deleteCinema(cinema);
    }

    private void createObjects(Cinema cinema, CinemaDto cinemaDto) {

        cinemaDto.setId(1L);
        cinemaDto.setAddress("Tudor Vladimirescu");
        cinemaDto.setName("CinemaCity");

        cinema.setId(1L);
        cinema.setAddress("Tudor Vladimirescu");
        cinema.setName("CinemaCity");
    }

    @After
    public void tearDown() {
        cinema = null;
        cinemaDto = null;
    }


}
