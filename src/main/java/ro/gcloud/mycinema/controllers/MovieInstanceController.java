package ro.gcloud.mycinema.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.gcloud.mycinema.dto.MovieInstanceDto;
import ro.gcloud.mycinema.entity.MovieInstance;
import ro.gcloud.mycinema.exceptions.BadRequestException;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.services.MovieInstanceService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

@RestController
@RequestMapping(path = "/api/movie-instances")
public class MovieInstanceController {

    private final ModelMapper modelMapper;
    private final MovieInstanceService movieInstanceService;

    @Autowired
    public MovieInstanceController(ModelMapper modelMapper, MovieInstanceService movieInstanceService) {
        this.modelMapper = modelMapper;
        this.movieInstanceService = movieInstanceService;
    }

    private Logger logger = LogManager.getLogger(this.getClass());


    @GetMapping
    public Collection<MovieInstance> getAllMovieInstances() {
        return movieInstanceService.getAllMovieInstances();
    }

    @GetMapping(value = "/{id}")
    public MovieInstance getMovieInstanceById(@PathVariable("id") Long id) throws NotFoundException {
        logger.info("Retrieving instance with id=" + id);
        return movieInstanceService.getMovieInstanceById(id);
    }

    @PostMapping
    public ResponseEntity<MovieInstance> saveMovieInstance(@RequestBody MovieInstanceDto movieInstanceToSave) throws URISyntaxException {
        MovieInstance movieInstance = movieInstanceService.saveMovieInstance(modelMapper.map(movieInstanceToSave, MovieInstance.class));
        return ResponseEntity.created(new URI("/api/movie-instances/" + movieInstance.getId())).body(movieInstance);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateMovieInstance(@PathVariable Long id, @RequestBody MovieInstanceDto movieInstanceToUpdate) throws BadRequestException, NotFoundException, URISyntaxException {
        if (!id.equals(movieInstanceToUpdate.getId())) {
            logger.warn("The ids do not match: received id=" + id + " in path and id=" + movieInstanceToUpdate.getId() + " in entity!");
            throw new BadRequestException("Different ids: " + id + " from PathVariable and " + movieInstanceToUpdate.getId() + " from RequestBody");
        }
        MovieInstance movieInstanceDb = movieInstanceService.getMovieInstanceById(id);
        modelMapper.map(movieInstanceToUpdate, movieInstanceDb);
        movieInstanceService.saveMovieInstance(movieInstanceDb);
        return ResponseEntity.ok(new URI("/api/movie-instances/" + movieInstanceDb.getId()));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovieInstance(@PathVariable Long id) throws NotFoundException {
        MovieInstance movieInstance = movieInstanceService.getMovieInstanceById(id);
        movieInstanceService.deleteMovieInstance(movieInstance);
    }


}
