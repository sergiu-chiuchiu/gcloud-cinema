package ro.gcloud.mycinema.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.gcloud.mycinema.dto.MovieDto;
import ro.gcloud.mycinema.entity.Movie;
import ro.gcloud.mycinema.exceptions.BadRequestException;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.services.MovieService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

@RestController
@RequestMapping(path = "/api/movies")
public class MovieController {
    private final MovieService movieService;
    private final ModelMapper modelMapper;

    @Autowired
    public MovieController(MovieService movieService, ModelMapper modelMapper) {
        this.movieService = movieService;
        this.modelMapper = modelMapper;
    }

    private Logger logger = LogManager.getLogger(this.getClass());


    @GetMapping
    public Collection<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping(value = "/{id}")
    public Movie getMovieById(@PathVariable("id") Long id) throws NotFoundException {
        logger.info("Searching for movie with id: " + id);
        return movieService.getMovieById(id);
    }

    @PostMapping
    public ResponseEntity<Movie> saveMovie(@RequestBody MovieDto movieToSave) throws URISyntaxException {
        Movie movie = movieService.saveMovie(modelMapper.map(movieToSave, Movie.class));
        return ResponseEntity.created(new URI("/api/movies/" + movie.getId())).body(movie);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity updateMovie(@PathVariable Long id, @RequestBody MovieDto movieToUpdate) throws BadRequestException, NotFoundException, URISyntaxException {
        if (!id.equals(movieToUpdate.getId())) {
            logger.warn("The ids do not match: received id=" + id + " in path and id=" + movieToUpdate.getId() + " in entity!");
            throw new BadRequestException("Different ids: " + id + " from PathVariable and " + movieToUpdate.getId() + " from RequestBody");
        }
        Movie movieDb = movieService.getMovieById(id);
        modelMapper.map(movieToUpdate, movieDb);
        movieService.saveMovie(movieDb);
        return ResponseEntity.ok(new URI("/api/movies/" + movieDb.getId()));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable Long id) throws NotFoundException {
        Movie movie = movieService.getMovieById(id);
        movieService.deleteMovie(movie);
    }
}
