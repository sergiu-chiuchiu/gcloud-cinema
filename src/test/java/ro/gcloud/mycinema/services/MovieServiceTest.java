package ro.gcloud.mycinema.services;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.gcloud.mycinema.entity.Movie;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.repositories.MovieRepository;
import ro.gcloud.mycinema.services.impl.MovieServiceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class MovieServiceTest {

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    MovieServiceImpl movieService;

    Movie movie;
    List<Movie> listOfMovies;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Inception");
        movie.setDescription("A must see movie");
        movie.setDurationMinutes(134);

        listOfMovies = new ArrayList<>();
        listOfMovies.add(movie);
    }

    @Test
    public void shouldReturnMovieById() throws NotFoundException {
        // arrange
        when(movieRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(movie));


        //act
        Movie movieById = movieService.getMovieById(1L);

        //assert
        Assertions.assertThat(movieById).isNotNull();
        Assertions.assertThat(movieById).isEqualToComparingFieldByFieldRecursively(movie);

    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundExceptionWhenMovieById() throws NotFoundException {
        // arrange
        when(movieRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        //act
        Movie movieById = movieService.getMovieById(2L);

        //assert
        fail();
    }


    @Test
    public void shouldReturnAllMovies() throws NotFoundException {
        // arrange
        when(movieRepository.findAll()).thenReturn(listOfMovies);

        //act
        Collection<Movie> allMovies = movieService.getAllMovies();

        //assert
        Assertions.assertThat(allMovies).isNotNull();
        Assertions.assertThat(allMovies.iterator().next()).isEqualToComparingFieldByFieldRecursively(movie);
        Assertions.assertThat(allMovies.size()).isEqualTo(1);
    }

    @Test
    public void shouldSaveMovie()  {

        when(movieRepository.save(movie)).thenReturn(movie);

        Movie savedMovie = movieService.saveMovie(movie);

        verify(movieRepository, times(1)).save(movie);
        Assertions.assertThat(savedMovie).isNotNull();
        Assertions.assertThat(savedMovie).isEqualToComparingFieldByFieldRecursively(movie);
    }

    @Test
    public void shouldDeleteMovieById()  {

        when(movieRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(movie));

        movieService.deleteMovieById(1L);

        verify(movieRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldDeleteMovie()  {

        when(movieRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(movie));

        movieService.deleteMovie(movie);

        verify(movieRepository, times(1)).delete(movie);
    }


    @Test
    public void shouldReturnIfMovieExists() throws NotFoundException {
        // arrange
        when(movieRepository.existsById(1L)).thenReturn(true);


        //act
        Boolean movieExistsById = movieService.movieExists(1L);

        //assert
        Assertions.assertThat(movieExistsById).isTrue();
        verify(movieRepository, times(1)).existsById(1L);

    }

}
