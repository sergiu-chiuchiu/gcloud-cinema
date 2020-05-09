package ro.gcloud.mycinema.services;

import ro.gcloud.mycinema.entity.Movie;
import ro.gcloud.mycinema.exceptions.NotFoundException;

import java.util.Collection;

public interface MovieService {
    Collection<Movie> getAllMovies();

    Movie getMovieById(Long id) throws NotFoundException;

    Movie saveMovie(Movie movie);

    void deleteMovieById(Long id);

    void deleteMovie(Movie movie);

    Boolean movieExists(Long id);
}
