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
import ro.gcloud.mycinema.dto.MovieRoomDto;
import ro.gcloud.mycinema.entity.MovieRoom;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.services.impl.MovieRoomServiceImpl;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class MovieRoomControllerTest {

    @Mock
    private MovieRoomServiceImpl movieRoomService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    MovieRoomController movieRoomController;

    MovieRoomDto movieRoomDto = new MovieRoomDto();
    MovieRoom movieRoom = new MovieRoom();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnResponseEntityForSave() throws URISyntaxException {

        // arrange
        createObjects(movieRoom, movieRoomDto);
        Mockito.when(movieRoomService.saveMovieRoom(movieRoom)).thenReturn(movieRoom);
        Mockito.when(modelMapper.map(movieRoomDto, MovieRoom.class)).thenReturn(movieRoom);

        // act
        ResponseEntity<MovieRoom> movieRoomResponseEntity = movieRoomController.saveMovieRoomResponseEntity(movieRoomDto);

        // assert
        Assertions.assertThat(movieRoomResponseEntity).isNotNull();
        Assertions.assertThat(movieRoomResponseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());

        Assertions.assertThat(movieRoomResponseEntity.getBody()).isEqualToComparingFieldByFieldRecursively(movieRoom);
        Assertions.assertThat(movieRoomResponseEntity.getHeaders().getLocation().toString()).isEqualTo("/api/movie-rooms/1");

    }

    @Test
    public void shouldReturnAllMovieRoomsByCinemaId() {
        createObjects(movieRoom, movieRoomDto);
        List<MovieRoom> movieRoomList = new ArrayList<>();
        movieRoomList.add(movieRoom);

        // arrange
        Mockito.when(movieRoomService.getAllMovieRoomsByCinemaId(1L)).thenReturn(movieRoomList);

        // act
        List<MovieRoom> movieRoomListResponse = movieRoomController.getAlMovieRoomsByCinemaid(1L);

        // assert

        verify(movieRoomService, times(1)).getAllMovieRoomsByCinemaId(1L);
        Assertions.assertThat(movieRoomListResponse).isNotNull();
        Assertions.assertThat(movieRoomListResponse.get(0)).isEqualToComparingFieldByFieldRecursively(movieRoom);
    }

    @Test
    public void shouldDeleteMovieRoom() throws NotFoundException {
        createObjects(movieRoom, movieRoomDto);

        // arrange
        when(movieRoomService.getMovieRoomById(1L)).thenReturn(movieRoom);

        // act
        movieRoomController.deleteMovieRoom(1L);

        // assert
        verify(movieRoomService, times(1)).deleteMovieRoom(movieRoom);
    }

    private void createObjects(MovieRoom movieRoom, MovieRoomDto movieRoomDto) {

        movieRoomDto.setId(1L);
        movieRoomDto.setCapacity(200);
        movieRoomDto.setName("MovieRoom3D");

        movieRoom.setId(1L);
        movieRoomDto.setCapacity(200);
        movieRoom.setName("MovieRoom3D");
    }

    @After
    public void tearDown() {
        movieRoom = null;
        movieRoomDto = null;
    }

}
