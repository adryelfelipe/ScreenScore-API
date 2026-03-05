package ctw.screenscoreapi.Movies.application.service;

import ctw.screenscoreapi.Movies.application.dtos.create.CreateMovieRequest;
import ctw.screenscoreapi.Movies.application.dtos.get.GetListOfExternalMoviesResponse;
import ctw.screenscoreapi.Movies.application.dtos.get.GetMovieResponse;
import ctw.screenscoreapi.Movies.application.dtos.get.GetListOfMoviesResponse;
import ctw.screenscoreapi.Movies.application.dtos.update.UpdateMovieRequest;
import ctw.screenscoreapi.Movies.application.exceptions.MovieNotFoundByIdException;
import ctw.screenscoreapi.Movies.application.exceptions.MovieTitleAlreadyUsedException;
import ctw.screenscoreapi.Movies.domain.enums.Genre;
import ctw.screenscoreapi.Share.exception.categories.NoContentToUpdateException;
import ctw.screenscoreapi.Movies.application.mapper.MovieMapper;
import ctw.screenscoreapi.Movies.domain.MovieEntity;
import ctw.screenscoreapi.Movies.domain.repository.MovieRepository;
import ctw.screenscoreapi.Movies.infra.feign.MovieApiClient;
import ctw.screenscoreapi.Movies.infra.feign.MovieApiResponse;
import ctw.screenscoreapi.Movies.infra.mapper.TmdbMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.cfg.MapperBuilder;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    // Atributos
    private MovieApiClient movieApiClient;
    private MovieMapper movieMapper;
    private TmdbMapper tmdbMapper;
    private MovieRepository movieRepository;

    @Value("${themoviedb.apikey}")
    private String themoviedbApiKey;

    // Construtor
    public MovieService(MovieApiClient movieApiClient, MovieMapper movieMapper, MovieRepository movieRepository, TmdbMapper tmdbMapper, MovieRepository movieDao, MapperBuilder mapperBuilder) {
        this.movieApiClient = movieApiClient;
        this.movieMapper = movieMapper;
        this.movieRepository = movieRepository;
        this.tmdbMapper = tmdbMapper;
    }

    // Metodos
    public long create(CreateMovieRequest request) {
        String title = request.title();
        Optional<MovieEntity> optionalMovie = movieRepository.findByExactTitle(title);

        if(optionalMovie.isPresent()) {
            throw new MovieTitleAlreadyUsedException(title);
        }

        MovieEntity movie = movieMapper.toEntity(request);
        return movieRepository.create(movie);
    }

    public GetListOfExternalMoviesResponse getExternal(String title) {
        MovieApiResponse movieApiResponse = movieApiClient.search(title, "pt-BR", themoviedbApiKey);
        List<MovieEntity> movies = tmdbMapper.toDomainEntities(movieApiResponse.getResults());

        return tmdbMapper.toResponseEntities(movies);
    }

    public GetMovieResponse getById(long id) {
        MovieEntity movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundByIdException(id));                                             ;

        return movieMapper.toResponse(movie);
    }

    public GetListOfMoviesResponse getMovies(String title, List<Genre> genres) {
        if(title != null || genres == null){
            List<MovieEntity> movieEntities = movieRepository.findMovieByFilter(title, genres);
            List<GetMovieResponse> movieResponses = movieEntities.stream().map(movieMapper::toResponse).toList();

            return new GetListOfMoviesResponse(movieResponses);
        }

        List<MovieEntity> movies = movieRepository.getAllMovies();

        return movieMapper.toResponse(movies);
    }

    public void delete(long id) {
        long deletedMovies = movieRepository.delete(id);

        if(deletedMovies == 0) {
            throw new MovieNotFoundByIdException(id);
        }
    }

    public void update(long id, UpdateMovieRequest request) {
        MovieEntity movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundByIdException(id));

        if (request.title() == null &&
                request.originalLanguage() == null &&
                request.originalTitle() == null &&
                request.adult() == null &&
                request.releaseDate() == null &&
                request.posterImage() == null &&
                request.overview() == null &&
                request.genres() == null) {

            throw new NoContentToUpdateException();
        }

        if(request.title() != null) {
            Optional<MovieEntity> optionalMovie = movieRepository.findByExactTitle(request.title());

            if(optionalMovie.isPresent()) {
                throw new MovieTitleAlreadyUsedException(request.title());
            }

            movie.setTitle(request.title());
        }

        if(request.originalLanguage() != null) {
            movie.setOriginalLanguage(request.originalLanguage());
        }

        if(request.originalTitle() != null) {
            movie.setOriginalTitle(request.originalTitle());
        }

        if(request.adult() != null) {
            movie.setAdult(request.adult());
        }

        if(request.releaseDate() != null) {
            movie.setReleaseDate(request.releaseDate());
        }

        if(request.posterImage() != null) {
            movie.setPosterImage(request.posterImage());
        }

        if(request.overview() != null) {
            movie.setOverview(request.overview());
        }

        if(request.genres() != null) {
            movie.setGenres(request.genres());
        }

        movieRepository.update(movie);
    }
}
