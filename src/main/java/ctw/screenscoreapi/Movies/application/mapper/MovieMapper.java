package ctw.screenscoreapi.Movies.application.mapper;

import ctw.screenscoreapi.Movies.application.dtos.create.CreateMovieRequest;
import ctw.screenscoreapi.Movies.application.dtos.get.GetMovieResponse;
import ctw.screenscoreapi.Movies.domain.MovieEntity;
import ctw.screenscoreapi.Movies.infra.feign.MovieApiEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieMapper {
    public MovieEntity toEntity(MovieApiEntity apiEntity) {

        return new MovieEntity(
                null,
                apiEntity.getPoster_path(),
                apiEntity.getRelease_date(),
                apiEntity.isAdult(),
                apiEntity.getOriginal_title(),
                apiEntity.getOriginal_language(),
                apiEntity.getTitle(),
                apiEntity.getOverview(),
                apiEntity.getGenre_ids()
                );
    }

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
                request.genreIds()
        );
    }

    public GetMovieResponse toResponse(List<MovieApiEntity> moviesApiList) {
        List<MovieEntity> moviesList = moviesApiList.stream().map(this::toEntity).toList();

        return new GetMovieResponse(
               moviesList
        );
    }
}
