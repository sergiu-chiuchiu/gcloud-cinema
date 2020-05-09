package ro.gcloud.mycinema.services;

import ro.gcloud.mycinema.entity.MovieInstance;
import ro.gcloud.mycinema.exceptions.NotFoundException;

import java.util.Collection;

public interface MovieInstanceService {


    Collection<MovieInstance> getAllMovieInstances();

    MovieInstance getMovieInstanceById(Long id) throws NotFoundException;

    MovieInstance saveMovieInstance(MovieInstance movieInstance);

    void deleteMovieInstance(MovieInstance movieInstance);
}
