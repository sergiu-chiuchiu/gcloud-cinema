package ro.gcloud.mycinema.services;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.gcloud.mycinema.entity.Cinema;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.repositories.CinemaRepository;
import ro.gcloud.mycinema.services.impl.CinemaServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class CinemaServiceTest {


    @Mock
    CinemaRepository cinemaRepository;

    @InjectMocks
    CinemaServiceImpl cinemaService;

    Cinema cinema;
    List<Cinema> listOfCinemas;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        cinema = new Cinema();
        cinema.setId(1L);
        cinema.setName("Cinema City");
        cinema.setAddress("Tudor Vladimirescu");

        listOfCinemas = new ArrayList<>();
        listOfCinemas.add(cinema);
    }

    @Test
    public void shouldReturnCinemaById() throws NotFoundException {
        // arrange
        when(cinemaRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(cinema));


        //act
        Cinema cinemaById = cinemaService.getCinemaById(1L);

        //assert
        Assertions.assertThat(cinemaById).isNotNull();
        Assertions.assertThat(cinemaById).isEqualToComparingFieldByFieldRecursively(cinema);

    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundExceptionWhenCinemaById() throws NotFoundException {
        // arrange
        when(cinemaRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        //act
        Cinema cinemaById = cinemaService.getCinemaById(2L);

        //assert
        fail();
    }


    @Test
    public void shouldReturnAllCinemas() throws NotFoundException {
        // arrange
        when(cinemaRepository.findAll()).thenReturn(listOfCinemas);

        //act
        List<Cinema> allCinemas = cinemaService.getAll();

        //assert
        Assertions.assertThat(allCinemas).isNotNull();
        Assertions.assertThat(allCinemas.get(0)).isEqualToComparingFieldByFieldRecursively(cinema);
        Assertions.assertThat(allCinemas.size()).isEqualTo(1);
    }

    @Test
    public void shouldSaveCinema()  {

        when(cinemaRepository.save(cinema)).thenReturn(cinema);

        Cinema savedCinema = cinemaService.saveCinema(cinema);

        verify(cinemaRepository, times(1)).save(cinema);
        Assertions.assertThat(savedCinema).isNotNull();
        Assertions.assertThat(savedCinema).isEqualToComparingFieldByFieldRecursively(cinema);
    }

    @Test
    public void shouldReturnCinemaByMovieRoomsCapacity() throws NotFoundException {
        // arrange
        when(cinemaRepository.getCinemaByMovieRoomsCapacity(200)).thenReturn(listOfCinemas);

        //act
        List<Cinema> allCinemas = cinemaService.getCinemaByMovieRoomsCapacity(200);

        //assert
        Assertions.assertThat(allCinemas).isNotNull();
        Assertions.assertThat(allCinemas.get(0)).isEqualToComparingFieldByFieldRecursively(cinema);
        Assertions.assertThat(allCinemas.size()).isEqualTo(1);
    }

    @Test
    public void shouldDeleteCinema()  {

        when(cinemaRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(cinema));

        cinemaService.deleteCinema(cinema);

        verify(cinemaRepository, times(1)).delete(cinema);
    }


}
