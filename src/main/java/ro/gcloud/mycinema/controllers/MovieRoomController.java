package ro.gcloud.mycinema.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.gcloud.mycinema.dto.MovieRoomDto;
import ro.gcloud.mycinema.entity.MovieRoom;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.services.MovieRoomService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "api/movie-rooms")
public class MovieRoomController {

    private final MovieRoomService movieRoomService;
    private final ModelMapper modelMapper;

    @Autowired
    public MovieRoomController(MovieRoomService movieRoomService, ModelMapper modelMapper) {
        this.movieRoomService = movieRoomService;
        this.modelMapper = modelMapper;
    }

    private Logger logger = LogManager.getLogger(this.getClass());

    @GetMapping
    public List<MovieRoom> getAllMovieRooms() {
        return movieRoomService.getAll();
    }

    @PostMapping
    public ResponseEntity<MovieRoom> saveMovieRoomResponseEntity(@RequestBody MovieRoomDto movieRoomDto) throws URISyntaxException {
        MovieRoom movieRoom = movieRoomService.saveMovieRoom(modelMapper.map(movieRoomDto, MovieRoom.class));
        return ResponseEntity.created(new URI("/api/movie-rooms/" + movieRoom.getId())).body(movieRoom);
    }

    @GetMapping(value = "/filter/{cinemaId}")
    public List<MovieRoom> getAlMovieRoomsByCinemaid(@PathVariable("cinemaId") Long cinemaId) {
        logger.info("Returning instance with id=" + cinemaId);
        return movieRoomService.getAllMovieRoomsByCinemaId(cinemaId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void deleteMovieRoom(@PathVariable("id") Long id) throws NotFoundException {
        MovieRoom movieRoom = movieRoomService.getMovieRoomById(id);
        logger.info("The Movie Room to be deleted has the name: " + movieRoom.getName() + " and the id " + id);
        movieRoomService.deleteMovieRoom(movieRoom);
    }
}
