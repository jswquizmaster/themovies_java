package de.schuwue.themovies.service;

import de.schuwue.themovies.entity.Movie;
import de.schuwue.themovies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public String createMovie(Movie movie){
        try {
            if (!movieRepository.existsByTmdbId(movie.getTmdbId())){
                movie.setId(null == movieRepository.findMaxId() ? 0 : movieRepository.findMaxId() + 1);
                movieRepository.save(movie);
                return "Movie record created successfully.";
            } else {
                return "Movie already exists in the database.";
            }
        } catch (Exception e){
            throw e;
        }
    }

    public Page<Movie> readMovies(PageRequest pageRequest){
        return movieRepository.findAll(pageRequest);

    }

    public Movie showMovieByTmdbId(Integer tmdbId) {
        return movieRepository.findByTmdbId(tmdbId);
    }

    @Transactional
    public String updateMovie(Movie movie){
        if (movieRepository.existsById(movie.getId())){
            try {
                Movie movieToBeUpdate = movieRepository.findById(movie.getId()).get();
                movieToBeUpdate.setTmdbId(movie.getTmdbId());
                movieRepository.save(movieToBeUpdate);

                return "Movie record updated.";
            } catch (Exception e){
                throw e;
            }
        } else {
            return "Movie does not exists in the database.";
        }
    }

    @Transactional
    public String deleteMovie(Movie movie){
        if (movieRepository.existsById(movie.getId())){
            try {
                Movie movieToBeDeleted = movieRepository.findById(movie.getId()).get();
                movieRepository.delete(movieToBeDeleted);
                return "Movie record deleted successfully.";
            } catch (Exception e){
                throw e;
            }

        } else {
            return "Movie does not exist";
        }
    }
}
