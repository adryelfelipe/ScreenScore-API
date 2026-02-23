package ctw.screenscoreapi.Movies.application.dtos.get;

import ctw.screenscoreapi.Movies.domain.MovieEntity;
import ctw.screenscoreapi.Movies.domain.enums.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record GetMovieResponse(
        String title,
        String originalLanguage,
        String originalTitle,
        Boolean adult,
        String releaseDate,
        String posterImage,
        String overview,
        List<Genre> genres
)  {}

