package ctw.screenscoreapi.Movies.domain.repository;

import ctw.screenscoreapi.Movies.domain.MovieEntity;

import java.util.Optional;

public interface MovieRepository {
    void create(MovieEntity movie);
    Optional<MovieEntity> findByTitle(String title);
}
