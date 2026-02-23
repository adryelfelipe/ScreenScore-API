package ctw.screenscoreapi.Movies.application.mapper;

import ctw.screenscoreapi.Movies.application.dtos.create.CreateMovieRequest;
import ctw.screenscoreapi.Movies.application.dtos.get.GetExternalMovieResponse;
import ctw.screenscoreapi.Movies.application.dtos.get.GetMovieResponse;
import ctw.screenscoreapi.Movies.application.exceptions.TmdbUnkwonGenreException;
import ctw.screenscoreapi.Movies.domain.MovieEntity;
import ctw.screenscoreapi.Movies.domain.enums.Genre;
import ctw.screenscoreapi.Movies.infra.feign.MovieApiEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieMapper {
    public MovieEntity toEntity(MovieApiEntity apiEntity) {
        List<Genre> genres = apiEntity.getGenre_ids().stream().map(this::toMovieGenre).toList();

        return new MovieEntity(
                null,
                apiEntity.getPoster_path(),
                apiEntity.getRelease_date(),
                apiEntity.isAdult(),
                apiEntity.getOriginal_title(),
                apiEntity.getOriginal_language(),
                apiEntity.getTitle(),
                apiEntity.getOverview(),
                genres
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
                request.genres()
        );
    }

    public List<MovieEntity> toEntity(List<MovieApiEntity> moviesApiList) {
        return moviesApiList.stream().map(this::toEntity).toList();
    }

    public GetMovieResponse toResponseEntity(MovieEntity movieEntity) {
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

    public GetExternalMovieResponse toResponse(List<MovieEntity> moviesList) {
        List<GetMovieResponse> movieResponses = moviesList.stream().map(this::toResponseEntity).toList();

        return new GetExternalMovieResponse(movieResponses);
    }

    private Genre toMovieGenre (Integer apiGenreId) {
       return switch (apiGenreId) {
           case 12 -> Genre.AVENTURA;
           case 14 -> Genre.FANTASIA;
           case 16 -> Genre.ANIMACAO;
           case 18 -> Genre.DRAMA;
           case 27 -> Genre.TERROR;
           case 28 -> Genre.ACAO;
           case 35 -> Genre.COMEDIA;
           case 36 -> Genre.HISTORIA;
           case 37 -> Genre.FAROESTE;
           case 53 -> Genre.THRILLER;
           case 80 -> Genre.CRIME;
           case 99 -> Genre.DOCUMENTARIO;
           case 878 -> Genre.FICCAO_CIENTIFICA;
           case 9648 -> Genre.MISTERIO;
           case 10402 -> Genre.MUSICA;
           case 10749 -> Genre.ROMANCE;
           case 10751 -> Genre.FAMILIA;
           case 10752 -> Genre.GUERRA;
           case 10770 -> Genre.CINEMA_TV;
           case null -> null;
           default -> throw new TmdbUnkwonGenreException(apiGenreId);
        };
    }
}
