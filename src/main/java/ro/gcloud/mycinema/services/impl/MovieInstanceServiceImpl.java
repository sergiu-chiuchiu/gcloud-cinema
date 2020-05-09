package ro.gcloud.mycinema.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.gcloud.mycinema.entity.MovieInstance;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.repositories.MovieInstanceRepository;
import ro.gcloud.mycinema.services.MovieInstanceService;

import java.util.Collection;

@Service
public class MovieInstanceServiceImpl implements MovieInstanceService {

    public final MovieInstanceRepository movieInstanceRepository;

    @Autowired
    public MovieInstanceServiceImpl(MovieInstanceRepository movieInstanceRepository) {
        this.movieInstanceRepository = movieInstanceRepository;
    }

    private Logger logger = LogManager.getLogger(this.getClass());


    @Override
    public Collection<MovieInstance> getAllMovieInstances() {
        return movieInstanceRepository.findAll();
    }

    @Override
    public MovieInstance getMovieInstanceById(Long id) throws NotFoundException {
        logger.info("Attempting to fetch from DB the movie instance with id=" + id);
        return movieInstanceRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("MovieInstance with id=%s was not found.", id)));
    }

    @Override
    public MovieInstance saveMovieInstance(MovieInstance movieInstance) {
        return movieInstanceRepository.save(movieInstance);
    }

    @Override
    public void deleteMovieInstance(MovieInstance movieInstance) {
        movieInstance.removeMovieInstance();
        if (movieInstance.getMovie() != null || movieInstance.getMovieRoom() != null || movieInstance.getCinema() != null) {
            logger.error("Could not remove Movie Instance Child from Parent. The instance will not be deleted");
        }
        movieInstanceRepository.delete(movieInstance);
    }
}
