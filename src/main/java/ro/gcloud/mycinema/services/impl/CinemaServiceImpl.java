package ro.gcloud.mycinema.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.gcloud.mycinema.entity.Cinema;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.repositories.CinemaRepository;
import ro.gcloud.mycinema.services.CinemaService;

import java.util.List;

@Service
public class CinemaServiceImpl implements CinemaService {
    @Autowired
    private CinemaRepository cinemaRepository;

    private Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public List<Cinema> getAll() {
        return cinemaRepository.findAll();
    }

    @Override
    public Cinema saveCinema(Cinema cinema) {
        logger.info("Attempting to persist in DB the cinema with the name: " + cinema.getName() + " and id: " + cinema.getId());
        return cinemaRepository.save(cinema);
    }

    @Override
    public List<Cinema> getCinemaByMovieRoomsCapacity(Integer capacity) {
        return cinemaRepository.getCinemaByMovieRoomsCapacity(capacity);
    }

    @Override
    public Cinema getCinemaById(Long id) throws NotFoundException {
        logger.info("Attempting to fetch from DB the cinema with id=" + id);
        return cinemaRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Person with id=%s was not found.", id)));
    }

    @Override
    public void deleteCinema(Cinema cinema) {
        cinemaRepository.delete(cinema);
    }

}
