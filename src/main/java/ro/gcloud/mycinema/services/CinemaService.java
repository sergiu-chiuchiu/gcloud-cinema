package ro.gcloud.mycinema.services;

import ro.gcloud.mycinema.entity.Cinema;
import ro.gcloud.mycinema.exceptions.NotFoundException;

import java.util.List;

public interface CinemaService {

    List<Cinema> getAll();

    Cinema saveCinema(Cinema cinema);

    List<Cinema> getCinemaByMovieRoomsCapacity(Integer capacity);

    Cinema getCinemaById(Long id) throws NotFoundException;

    void deleteCinema(Cinema cinema);
}
