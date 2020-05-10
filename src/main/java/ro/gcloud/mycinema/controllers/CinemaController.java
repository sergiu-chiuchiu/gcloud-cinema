package ro.gcloud.mycinema.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.gcloud.mycinema.dto.CinemaDto;
import ro.gcloud.mycinema.entity.Cinema;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.services.CinemaService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/cinemas")
public class CinemaController {
    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private ModelMapper modelMapper;

    private Logger logger = LogManager.getLogger(this.getClass());

//    @GetMapping("/")
//    public String hello() {
//        return "Hello world!";
//    }

    @GetMapping
    public List<Cinema> getAllCinemas() {
        return cinemaService.getAll();
    }

    @PostMapping
    public ResponseEntity<Cinema> saveCinema(@RequestBody CinemaDto cinemaDto) throws URISyntaxException {
        Cinema cinema = cinemaService.saveCinema(modelMapper.map(cinemaDto, Cinema.class));
        return ResponseEntity.created(new URI("/api/cinemas/" + cinema.getId())).body(cinema);
    }

    @GetMapping(value = "/capacity")
    public List<Cinema> getCinemaByMovieRoomCapacity(@RequestParam("capacity") Integer capacity) {
        logger.info("Fetching all the cinemas with a Movie Room capacity of: " + capacity);
        return cinemaService.getCinemaByMovieRoomsCapacity(capacity);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void deleteCinema(@PathVariable("id") Long id) throws NotFoundException {
        Cinema cinema = cinemaService.getCinemaById(id);
        logger.info("The cinema to be deleted has the name: " + cinema.getName() + " and the id " + id);
        cinemaService.deleteCinema(cinema);
    }

}
