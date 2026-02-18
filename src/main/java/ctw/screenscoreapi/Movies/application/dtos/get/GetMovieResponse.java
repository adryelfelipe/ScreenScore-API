package ctw.screenscoreapi.Movies.application.dtos.get;

import ctw.screenscoreapi.Movies.domain.MovieEntity;

import java.util.List;

public record GetMovieResponse(
        List<MovieEntity> moviesList
)  {}

