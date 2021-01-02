package de.schuwue.themovies.controller;

import de.schuwue.themovies.entity.Movie;
import de.schuwue.themovies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "info", method = RequestMethod.GET)
    public String info() {
        return "Have fun with all your movies!";
    }

    @RequestMapping(value = "movies", method = RequestMethod.POST)
    public String createMovie(@RequestBody Movie movie) {
        return movieService.createMovie(movie);
    }

    @RequestMapping(value = "movies", method = RequestMethod.GET)
    public List<Movie> readMovies(
            @RequestParam(defaultValue = "-createdAt") String orderBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int paginate
    ) {
        Sort sort;

        orderBy = orderBy.replace("datum", "createdAt");

        if (orderBy.charAt(0) == '-') {
            sort = Sort.by(Sort.Direction.DESC, orderBy.substring(1));
        } else {
            sort = Sort.by(Sort.Direction.ASC, orderBy);
        }
        PageRequest pageRequest = PageRequest.of(page, paginate, sort);
        return movieService.readMovies(pageRequest).toList();
    }

    @RequestMapping(value = "movies/{id}/play", method = RequestMethod.GET)
    public String getWebPlayer(@PathVariable int id) {
        return "Web Player is not yet implemented. Otherwise I would have played movie id " + id + ":-(";
    }

    @RequestMapping(value = "movie", method = RequestMethod.GET)
    public Movie showMovie(@RequestParam int tmdbId) {
        return movieService.showMovieByTmdbId(tmdbId);
    }

    @RequestMapping(value = "movies", method = RequestMethod.PUT)
    public String updateMovie(@RequestBody Movie movie) {
        return movieService.updateMovie(movie);
    }

    @RequestMapping(value = "movies", method = RequestMethod.DELETE)
    public String deleteMovie(@RequestBody Movie movie) {
        return movieService.deleteMovie(movie);
    }
}
