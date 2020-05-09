package ro.gcloud.mycinema.services;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.gcloud.mycinema.entity.Cinema;
import ro.gcloud.mycinema.entity.MovieRoom;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.repositories.MovieRoomRepository;
import ro.gcloud.mycinema.services.impl.MovieRoomServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class MovieRoomServiceTest {
    @Mock
    MovieRoomRepository movieRoomRepository;

    @InjectMocks
    MovieRoomServiceImpl movieRoomService;

    MovieRoom movieRoom;
    List<MovieRoom> listOfMovieRooms;
    Cinema cinema;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        cinema = newCinema();

        movieRoom = new MovieRoom();
        movieRoom.setId(1L);
        movieRoom.setName("3D room");
        movieRoom.setCapacity(300);
        movieRoom.setCinema(cinema);


        listOfMovieRooms = new ArrayList<>();
        listOfMovieRooms.add(movieRoom);

        cinema.setMovieRooms(listOfMovieRooms);
    }


    @Test
    public void shouldReturnAllCinemas() throws NotFoundException {
        // arrange
        when(movieRoomRepository.findAll()).thenReturn(listOfMovieRooms);

        //act
        List<MovieRoom> allMovieRooms = movieRoomService.getAll();

        //assert
        Assertions.assertThat(allMovieRooms).isNotNull();
        Assertions.assertThat(allMovieRooms.get(0)).isEqualToComparingFieldByFieldRecursively(movieRoom);
        Assertions.assertThat(allMovieRooms.size()).isEqualTo(1);
    }

    @Test
    public void shouldSaveMovieRoom()  {

        when(movieRoomRepository.save(movieRoom)).thenReturn(movieRoom);

        MovieRoom savedMovieRoom = movieRoomService.saveMovieRoom(movieRoom);

        verify(movieRoomRepository, times(1)).save(movieRoom);
        Assertions.assertThat(savedMovieRoom).isNotNull();
        Assertions.assertThat(savedMovieRoom).isEqualToComparingFieldByFieldRecursively(movieRoom);
    }

    @Test
    public void shouldReturnAllMovieRoomsByCinemaId() {
        // arrange
        when(movieRoomRepository.getMovieRoomByCinema_Id(1L)).thenReturn(listOfMovieRooms);

        //act
        List<MovieRoom> allMovieRooms = movieRoomService.getAllMovieRoomsByCinemaId(1L);

        //assert
        Assertions.assertThat(allMovieRooms).isNotNull();
        Assertions.assertThat(allMovieRooms.get(0)).isEqualToComparingFieldByFieldRecursively(movieRoom);
        Assertions.assertThat(allMovieRooms.size()).isEqualTo(1);

    }

    @Test
    public void shouldReturnMovieRoomById() throws NotFoundException {
        // arrange
        when(movieRoomRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(movieRoom));

        //act
        MovieRoom movieRoomById = movieRoomService.getMovieRoomById(1L);

        //assert
        Assertions.assertThat(movieRoomById).isNotNull();
        Assertions.assertThat(movieRoomById).isEqualToComparingFieldByFieldRecursively(movieRoom);

    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundExceptionWhenMovieRoomById() throws NotFoundException {
        // arrange
        when(movieRoomRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        //act
        MovieRoom movieRoomById = movieRoomService.getMovieRoomById(2L);

        //assert
        fail();
    }


    @Test
    public void shouldDeleteMovieRoom()  {

        when(movieRoomRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(movieRoom));

        movieRoomService.deleteMovieRoom(movieRoom);

        Assertions.assertThat(movieRoom.getCinema()).isNull();
        Assertions.assertThat(cinema.getMovieRooms()).isEmpty();
        verify(movieRoomRepository, times(1)).delete(movieRoom);
    }

    private Cinema newCinema() {
        Cinema cinema = new Cinema();
        cinema.setId(1L);
        cinema.setAddress("address");
        cinema.setName("cinemaName");
        return cinema;
    }
}
