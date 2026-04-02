package ctw.screenscoreapi.Module.Movies.domain.repository;

import ctw.screenscoreapi.Module.Movies.domain.entity.MovieEntity;
import ctw.screenscoreapi.Module.Movies.domain.enums.Genre;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    long create(MovieEntity movie);
    List<MovieEntity> findMovieByFilter(String title, List<Genre> genres);
    Optional<MovieEntity> findByExactTitle(String title);
    int delete(long id);
    Optional<MovieEntity> findById(long id);
    List<MovieEntity> findAllMovies();
    void update(MovieEntity movie);
    List<MovieEntity> findTop10Movies();
}
