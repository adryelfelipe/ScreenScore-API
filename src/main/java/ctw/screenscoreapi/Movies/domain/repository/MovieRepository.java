package ctw.screenscoreapi.Movies.domain.repository;

import ctw.screenscoreapi.Movies.domain.entity.MovieEntity;
import ctw.screenscoreapi.Movies.domain.enums.Genre;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    long create(MovieEntity movie);
    List<MovieEntity> findMovieByFilter(String title, List<Genre> genres);
    Optional<MovieEntity> findByExactTitle(String title);
    int delete(long id);
    Optional<MovieEntity> findById(long id);
    List<MovieEntity> getAllMovies();
    void update(MovieEntity movie);
}
