package ctw.screenscoreapi.Module.Movies.infra.themoviedb.mapper;

import ctw.screenscoreapi.Module.Movies.application.dtos.get.GetExternalMovieResponse;
import ctw.screenscoreapi.Module.Movies.application.dtos.get.GetListOfExternalMoviesResponse;
import ctw.screenscoreapi.Module.Movies.infra.themoviedb.feign.TmdbUnkwonGenreException;
import ctw.screenscoreapi.Module.Movies.domain.entity.MovieEntity;
import ctw.screenscoreapi.Module.Movies.domain.enums.Genre;
import ctw.screenscoreapi.Module.Movies.infra.themoviedb.feign.models.MovieApiEntity;
import ctw.screenscoreapi.Module.Movies.infra.themoviedb.feign.models.detailed.DetailedMovieApiEntity;
import ctw.screenscoreapi.Module.Movies.infra.themoviedb.feign.models.detailed.DetailedMovieApiGenre;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TmdbMapper {
    public MovieEntity toDomainEntity(MovieApiEntity apiEntity) {
        List<Genre> genres = apiEntity.getGenre_ids().stream().map(this::toDomainGenre).toList();

        return new MovieEntity(
                apiEntity.getId(),
                apiEntity.getPoster_path(),
                apiEntity.getRelease_date(),
                apiEntity.isAdult(),
                apiEntity.getOriginal_title(),
                apiEntity.getOriginal_language(),
                apiEntity.getTitle(),
                apiEntity.getOverview(),
                genres,
                null,
                null
        );
    }

    public MovieEntity toDomainEntity(DetailedMovieApiEntity apiEntity) {
        List<Genre> genres = apiEntity.getGenres().stream().map(this::toDomainGenre).toList();

        return new MovieEntity(
                apiEntity.getId(),
                apiEntity.getPoster_path(),
                apiEntity.getRelease_date(),
                apiEntity.isAdult(),
                apiEntity.getOriginal_title(),
                apiEntity.getOriginal_language(),
                apiEntity.getTitle(),
                apiEntity.getOverview(),
                genres,
                null,
                null
        );
    }

    public List<MovieEntity> toDomainEntities(List<MovieApiEntity> moviesApiList) {
        return moviesApiList.stream().map(this::toDomainEntity).toList();
    }

    public GetExternalMovieResponse toResponseEntity(MovieEntity movieEntity) {
        return new GetExternalMovieResponse(
                movieEntity.getTitle(),
                movieEntity.getId(),
                movieEntity.getOriginalLanguage(),
                movieEntity.getOriginalTitle(),
                movieEntity.isAdult(),
                movieEntity.getReleaseDate(),
                movieEntity.getPosterImage(),
                movieEntity.getOverview(),
                movieEntity.getGenres()
        );
    }

    public GetListOfExternalMoviesResponse toResponseEntities(List<MovieEntity> moviesList) {
        List<GetExternalMovieResponse> movieResponses = moviesList.stream().map(this::toResponseEntity).toList();

        return new GetListOfExternalMoviesResponse(movieResponses);
    }

    private Genre toDomainGenre (Integer apiGenreId) {
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

    private Genre toDomainGenre (DetailedMovieApiGenre apiGenre) {
        if(apiGenre == null) {
            return null;
        }

        return switch (apiGenre.getId()) {
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
            default -> throw new TmdbUnkwonGenreException(apiGenre.getId());
        };
    }
}
