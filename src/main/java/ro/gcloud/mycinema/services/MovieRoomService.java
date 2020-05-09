package ro.gcloud.mycinema.services;

import ro.gcloud.mycinema.entity.MovieRoom;
import ro.gcloud.mycinema.exceptions.NotFoundException;

import java.util.List;

public interface MovieRoomService {

    List<MovieRoom> getAll();

    MovieRoom saveMovieRoom(MovieRoom movieRoom);

    List<MovieRoom> getAllMovieRoomsByCinemaId(Long cinemaId);

    MovieRoom getMovieRoomById(Long id) throws NotFoundException;

    void deleteMovieRoom(MovieRoom movieRoom);
}
