package de.schuwue.themovies.repository;

import de.schuwue.themovies.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    public boolean existsByTmdbId(Integer tmbdId);

    public Movie findByTmdbId(Integer tmdbId);

    @Query("select max(m.id) from Movie m")
    public Integer findMaxId();
}
