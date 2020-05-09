package ro.gcloud.mycinema.services;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.gcloud.mycinema.entity.Cinema;
import ro.gcloud.mycinema.entity.Movie;
import ro.gcloud.mycinema.entity.MovieInstance;
import ro.gcloud.mycinema.entity.MovieRoom;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.repositories.MovieInstanceRepository;
import ro.gcloud.mycinema.services.impl.MovieInstanceServiceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class MovieInstanceServiceTest {
    @Mock
    MovieInstanceRepository movieInstanceRepository;

    @InjectMocks
    MovieInstanceServiceImpl movieInstanceService;

    MovieInstance movieInstance;
    List<MovieInstance> listOfMovieInstances;
    Cinema cinema;
    Movie movie;
    MovieRoom movieRoom;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        cinema = newCinema();
        movie = newMovie();
        movieRoom = newMovieRoom();

        movieInstance = new MovieInstance();
        movieInstance.setId(1L);
        movieInstance.setStartDate(new Date());
        movieInstance.setEndDate(new Date());
        movieInstance.setAvailableSeats(122);
        movieInstance.setCinema(cinema);
        movieInstance.setMovie(movie);
        movieInstance.setMovieRoom(movieRoom);

        listOfMovieInstances = new ArrayList<>();
        listOfMovieInstances.add(movieInstance);

        cinema.setMovieInstances(listOfMovieInstances);
        movie.setMovieInstances(listOfMovieInstances);
        movieRoom.setMovieInstances(listOfMovieInstances);
    }


    @Test
    public void shouldReturnAllMovieInstances() throws NotFoundException {
        // arrange
        when(movieInstanceRepository.findAll()).thenReturn(listOfMovieInstances);

        //act
        Collection<MovieInstance> allMovieInstances = movieInstanceService.getAllMovieInstances();

        //assert
        Assertions.assertThat(allMovieInstances).isNotNull();
        Assertions.assertThat(allMovieInstances.iterator().next()).isEqualToComparingFieldByFieldRecursively(movieInstance);
        Assertions.assertThat(allMovieInstances.size()).isEqualTo(1);
    }

    @Test
    public void shouldSaveMovieInstance()  {

        when(movieInstanceRepository.save(movieInstance)).thenReturn(movieInstance);

        MovieInstance savedMovieInstance = movieInstanceService.saveMovieInstance(movieInstance);

        verify(movieInstanceRepository, times(1)).save(movieInstance);
        Assertions.assertThat(savedMovieInstance).isNotNull();
        Assertions.assertThat(savedMovieInstance).isEqualToComparingFieldByFieldRecursively(movieInstance);
    }

    @Test
    public void shouldReturnMovieInstanceById() throws NotFoundException {
        // arrange
        when(movieInstanceRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(movieInstance));

        //act
        MovieInstance movieInstanceById = movieInstanceService.getMovieInstanceById(1L);

        //assert
        Assertions.assertThat(movieInstanceById).isNotNull();
        Assertions.assertThat(movieInstanceById).isEqualToComparingFieldByFieldRecursively(movieInstance);

    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundExceptionWhenMovieInstanceById() throws NotFoundException {
        // arrange
        when(movieInstanceRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        //act
        MovieInstance movieInstanceById = movieInstanceService.getMovieInstanceById(2L);

        //assert
        fail();
    }


    @Test
    public void shouldDeleteMovieInstance()  {

        when(movieInstanceRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(movieInstance));

        movieInstanceService.deleteMovieInstance(movieInstance);

        Assertions.assertThat(movieInstance.getCinema()).isNull();
        Assertions.assertThat(cinema.getMovieInstances()).isEmpty();
        verify(movieInstanceRepository, times(1)).delete(movieInstance);
    }

    private Cinema newCinema() {
        Cinema cinema = new Cinema();
        cinema.setId(1L);
        cinema.setAddress("address");
        cinema.setName("cinemaName");
        return cinema;
    }

    private Movie newMovie() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Inception");
        movie.setDescription("A must see movie");
        movie.setDurationMinutes(134);
        return movie;
    }

    private MovieRoom newMovieRoom() {
        movieRoom = new MovieRoom();
        movieRoom.setId(1L);
        movieRoom.setName("3D room");
        movieRoom.setCapacity(300);
        return movieRoom;
    }

}
