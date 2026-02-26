package ctw.screenscoreapi.Movies.application.mapper;

import ctw.screenscoreapi.Movies.application.dtos.create.CreateMovieRequest;
import ctw.screenscoreapi.Movies.application.dtos.get.GetExternalMovieResponse;
import ctw.screenscoreapi.Movies.application.dtos.get.GetMovieResponse;
import ctw.screenscoreapi.Movies.application.exceptions.TmdbUnkwonGenreException;
import ctw.screenscoreapi.Movies.domain.MovieEntity;
import ctw.screenscoreapi.Movies.domain.enums.Genre;
import ctw.screenscoreapi.Movies.infra.feign.MovieApiEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieMapper {
    public MovieEntity toEntity(CreateMovieRequest request) {

        return new MovieEntity(
                null,
                request.posterImage(),
                request.releaseDate(),
                request.adult(),
                request.originalTitle(),
                request.originalLanguage(),
                request.title(),
                request.overview(),
                request.genres()
        );
    }

    public GetMovieResponse toResponse(MovieEntity movieEntity) {

        return new GetMovieResponse(
                movieEntity.getTitle(),
                movieEntity.getOriginalLanguage(),
                movieEntity.getOriginalTitle(),
                movieEntity.isAdult(),
                movieEntity.getReleaseDate(),
                movieEntity.getPosterImage(),
                movieEntity.getOverview(),
                movieEntity.getGenres()
        );
    }
}
