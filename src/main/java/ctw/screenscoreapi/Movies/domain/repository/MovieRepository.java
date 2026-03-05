package ctw.screenscoreapi.Movies.domain.repository;

import ctw.screenscoreapi.Movies.application.dtos.get.GetListOfMoviesResponse;
import ctw.screenscoreapi.Movies.domain.MovieEntity;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    long create(MovieEntity movie);
    List<MovieEntity> findByLikeTitle(String title);
    Optional<MovieEntity> findByExactTitle(String title);
    long delete(long id);
    Optional<MovieEntity> findById(long id);
    List<MovieEntity> getAllMovies();
    void update(MovieEntity movie);
}
