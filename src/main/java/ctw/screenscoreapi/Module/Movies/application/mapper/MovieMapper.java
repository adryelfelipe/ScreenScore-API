package ctw.screenscoreapi.Module.Movies.application.mapper;

import ctw.screenscoreapi.Module.Movies.application.dtos.create.CreateMovieRequest;
import ctw.screenscoreapi.Module.Movies.application.dtos.get.GetListOfMoviesResponse;
import ctw.screenscoreapi.Module.Movies.application.dtos.get.GetMovieResponse;
import ctw.screenscoreapi.Module.Movies.domain.entity.MovieEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieMapper {
    public MovieEntity toEntity(CreateMovieRequest request, String fileKey) {

        return new MovieEntity(
                null,
                fileKey,
                request.releaseDate(),
                request.adult(),
                request.originalTitle(),
                request.originalLanguage(),
                request.title(),
                request.overview(),
                request.genres(),
                null,
                null
        );
    }

    public GetMovieResponse toResponse(MovieEntity movieEntity) {

        return new GetMovieResponse(
                movieEntity.getId(),
                movieEntity.getTitle(),
                movieEntity.getOriginalLanguage(),
                movieEntity.getOriginalTitle(),
                movieEntity.isAdult(),
                movieEntity.getReleaseDate(),
                movieEntity.getPosterImage(),
                movieEntity.getOverview(),
                movieEntity.getGenres(),
                movieEntity.getAvaliationsIds(),
                movieEntity.getAverageScore()
        );
    }

    public GetListOfMoviesResponse toResponse(List<MovieEntity> movieEntityList) {
        List<GetMovieResponse> movies = movieEntityList
                .stream()
                .map(this::toResponse)
                .toList();

        return new GetListOfMoviesResponse(movies);
    }
}
