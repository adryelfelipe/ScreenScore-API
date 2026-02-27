package ctw.screenscoreapi.Movies.domain.repository;

import ctw.screenscoreapi.Movies.domain.MovieEntity;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    void create(MovieEntity movie);
    Optional<List<MovieEntity>> findByLikeTitle(String title);
    Optional<MovieEntity> findByExactTitle(String title);
}
