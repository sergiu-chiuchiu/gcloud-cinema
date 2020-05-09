package ro.gcloud.mycinema.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.gcloud.mycinema.entity.MovieRoom;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.repositories.MovieRoomRepository;
import ro.gcloud.mycinema.services.CinemaService;
import ro.gcloud.mycinema.services.MovieRoomService;

import java.util.List;

@Service
public class MovieRoomServiceImpl implements MovieRoomService {

    private MovieRoomRepository movieRoomRepository;
    private CinemaService cinemaService;

    @Autowired
    public MovieRoomServiceImpl(MovieRoomRepository movieRoomRepository, CinemaService cinemaService) {
        this.movieRoomRepository = movieRoomRepository;
        this.cinemaService = cinemaService;
    }
    private Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public List<MovieRoom> getAll() {
        return movieRoomRepository.findAll();
    }

    @Override
    public MovieRoom saveMovieRoom(MovieRoom movieRoom) {
        return movieRoomRepository.save(movieRoom);
    }

    @Override
    public List<MovieRoom> getAllMovieRoomsByCinemaId(Long cinemaId) {
        logger.info("Retrieving all the Movie Rooms belonging to the cinema with id=" + cinemaId);
        return movieRoomRepository.getMovieRoomByCinema_Id(cinemaId);
    }

    @Override
    public MovieRoom getMovieRoomById(Long id) throws NotFoundException {
        logger.info("Attempting to fetch from DB the Movie Room instance with id=" + id);
        return movieRoomRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Movie Room with id=%s was not found.", id)));
    }

    @Override
    public void deleteMovieRoom(MovieRoom movieRoom) {
        movieRoom.getCinema().removeMovieRoom(movieRoom);
        if (movieRoom.getCinema() != null)
            logger.error("Could not remove Movie Room Child from Cinema. The instance will not be deleted");
        movieRoomRepository.delete(movieRoom);
    }

}
