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
import ro.gcloud.mycinema.dto.MovieDto;
import ro.gcloud.mycinema.entity.Movie;
import ro.gcloud.mycinema.exceptions.BadRequestException;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.services.impl.MovieServiceImpl;

import java.net.URISyntaxException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class MovieControllerTest {


    @Mock
    private MovieServiceImpl movieService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    MovieController movieController;

    MovieDto movieDto = new MovieDto();
    Movie movie = new Movie();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnResponseEntityForSave() throws URISyntaxException {

        // arrange
        createObjects(movie, movieDto);
        Mockito.when(movieService.saveMovie(movie)).thenReturn(movie);
        Mockito.when(modelMapper.map(movieDto, Movie.class)).thenReturn(movie);

        // act
        ResponseEntity<Movie> movieResponseEntity = movieController.saveMovie(movieDto);

        // assert
        Assertions.assertThat(movieResponseEntity).isNotNull();
        Assertions.assertThat(movieResponseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(movieResponseEntity.getBody()).isEqualToComparingFieldByFieldRecursively(movie);
        Assertions.assertThat(movieResponseEntity.getHeaders().getLocation().toString()).isEqualTo("/api/movies/1");
    }

    @Test
    public void shouldReturnUpdatedMovie() throws BadRequestException, NotFoundException, URISyntaxException {
        createObjects(movie, movieDto);

        // arrange
        Mockito.when(movieService.getMovieById(1L)).thenReturn(movie);
        Mockito.when(movieService.saveMovie(movie)).thenReturn(movie);

        // act
        ResponseEntity movieResponseEntity = movieController.updateMovie(1L, movieDto);

        // assert
        verify(movieService, times(1)).saveMovie(movie);
        Assertions.assertThat(movieResponseEntity).isNotNull();
        Assertions.assertThat(movieResponseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void shouldDeleteMovie() throws NotFoundException {
        createObjects(movie, movieDto);

        // arrange
        when(movieService.getMovieById(1L)).thenReturn(movie);

        // act
        movieController.deleteMovie(1L);

        // assert
        verify(movieService, times(1)).deleteMovie(movie);
    }

    private void createObjects(Movie movie, MovieDto movieDto) {
        movieDto.setId(1L);
        movieDto.setTitle("Inception");
        movieDto.setDescription("A must see movie");
        movieDto.setDurationMinutes(134);

        movie.setId(1L);
        movie.setTitle("InceptionDto");
        movie.setDescription("A must see movieDto");
        movie.setDurationMinutes(192);
    }

    @After
    public void tearDown() {
        movie = null;
        movieDto = null;
    }

}
