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
import ro.gcloud.mycinema.dto.MovieInstanceDto;
import ro.gcloud.mycinema.entity.MovieInstance;
import ro.gcloud.mycinema.exceptions.BadRequestException;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.services.impl.MovieInstanceServiceImpl;

import java.net.URISyntaxException;
import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class MovieInstanceControllerTest {


    @Mock
    private MovieInstanceServiceImpl movieInstanceService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    MovieInstanceController movieInstanceController;

    MovieInstanceDto movieInstanceDto = new MovieInstanceDto();
    MovieInstance movieInstance = new MovieInstance();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnResponseEntityForSave() throws URISyntaxException {

        // arrange
        createObjects(movieInstance, movieInstanceDto);
        Mockito.when(movieInstanceService.saveMovieInstance(movieInstance)).thenReturn(movieInstance);
        Mockito.when(modelMapper.map(movieInstanceDto, MovieInstance.class)).thenReturn(movieInstance);

        // act
        ResponseEntity<MovieInstance> movieInstanceResponseEntity = movieInstanceController.saveMovieInstance(movieInstanceDto);

        // assert
        Assertions.assertThat(movieInstanceResponseEntity).isNotNull();
        Assertions.assertThat(movieInstanceResponseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(movieInstanceResponseEntity.getBody()).isEqualToComparingFieldByFieldRecursively(movieInstance);
        Assertions.assertThat(movieInstanceResponseEntity.getHeaders().getLocation().toString()).isEqualTo("/api/movie-instances/1");
    }

    @Test
    public void shouldReturnUpdatedMovieInstance() throws BadRequestException, NotFoundException, URISyntaxException {
        createObjects(movieInstance, movieInstanceDto);

        // arrange
        Mockito.when(movieInstanceService.getMovieInstanceById(1L)).thenReturn(movieInstance);
        Mockito.when(movieInstanceService.saveMovieInstance(movieInstance)).thenReturn(movieInstance);

        // act
        ResponseEntity movieInstanceResponseEntity = movieInstanceController.updateMovieInstance(1L, movieInstanceDto);

        // assert
        verify(movieInstanceService, times(1)).saveMovieInstance(movieInstance);
        Assertions.assertThat(movieInstanceResponseEntity).isNotNull();

    }

    @Test
    public void shouldDeleteMovieInstance() throws NotFoundException {

        // arrange
        createObjects(movieInstance, movieInstanceDto);
        when(movieInstanceService.getMovieInstanceById(1L)).thenReturn(movieInstance);

        // act
        movieInstanceController.deleteMovieInstance(1L);

        // assert
        verify(movieInstanceService, times(1)).deleteMovieInstance(movieInstance);
    }

    private void createObjects(MovieInstance movieInstance, MovieInstanceDto movieInstanceDto) {
        movieInstanceDto.setId(1L);
        movieInstanceDto.setStartDate(new Date());
        movieInstanceDto.setEndDate(new Date());
        movieInstanceDto.setAvailableSeats(122);

        movieInstance.setId(1L);
        movieInstance.setStartDate(new Date());
        movieInstance.setEndDate(new Date());
        movieInstance.setAvailableSeats(127);
    }

    @After
    public void tearDown() {
        movieInstance = null;
        movieInstanceDto = null;
    }

}
