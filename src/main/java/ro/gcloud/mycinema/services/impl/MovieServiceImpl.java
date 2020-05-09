package ro.gcloud.mycinema.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.gcloud.mycinema.entity.Movie;
import ro.gcloud.mycinema.exceptions.NotFoundException;
import ro.gcloud.mycinema.repositories.MovieRepository;
import ro.gcloud.mycinema.services.MovieService;

import java.util.Collection;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    private Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public Collection<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getMovieById(Long id) throws NotFoundException {
        logger.info("Retrieving the Movie with id=" + id);
        return movieRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Movie with id=%s was not found.", id)));
    }

    @Override
    public Movie saveMovie(Movie movie) {
        logger.info("Attempting to persist in DB the Movie with title: " + movie.getTitle());
        return movieRepository.save(movie);
    }

    @Override
    public void deleteMovieById(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public void deleteMovie(Movie movie) {
        movieRepository.delete(movie);
    }

    @Override
    public Boolean movieExists(Long id) {
        return movieRepository.existsById(id);
    }

}
