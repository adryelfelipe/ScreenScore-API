package ctw.screenscoreapi.Movies.domain.repository;

import ctw.screenscoreapi.Movies.domain.MovieEntity;

public interface MovieRepository {
    void create(MovieEntity movie);
}
