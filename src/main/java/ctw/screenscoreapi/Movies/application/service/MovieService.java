package ctw.screenscoreapi.Movies.application.service;

import ctw.screenscoreapi.Movies.application.dtos.create.CreateMovieRequest;
import ctw.screenscoreapi.Movies.application.dtos.get.GetExternalMovieResponse;
import ctw.screenscoreapi.Movies.application.dtos.get.GetMovieResponse;
import ctw.screenscoreapi.Movies.application.exceptions.MovieNotFoundByTitle;
import ctw.screenscoreapi.Movies.application.exceptions.MovieTitleAlreadyUsedException;
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
    private final MovieRepository movieDao;
    private final MapperBuilder mapperBuilder;
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
        this.movieDao = movieDao;
        this.mapperBuilder = mapperBuilder;
    }

    // Metodos
    public void create(CreateMovieRequest request) {
        String title = request.title();
        Optional<MovieEntity> optionalMovie = movieRepository.findByTitle(title);

        if(optionalMovie.isPresent()) {
            throw new MovieTitleAlreadyUsedException(title);
        }

        MovieEntity movie = movieMapper.toEntity(request);
        movieRepository.create(movie);
    }

    public GetExternalMovieResponse getExternal(String title) {
        MovieApiResponse movieApiResponse = movieApiClient.search(title, "pt-BR", themoviedbApiKey);
        List<MovieEntity> movies = tmdbMapper.toDomainEntities(movieApiResponse.getResults());

        return tmdbMapper.toResponseEntities(movies);
    }

    public GetMovieResponse get(String title) {
        Optional<MovieEntity> optionalMovie = movieDao.findByTitle(title);
        MovieEntity movie = optionalMovie.orElseThrow(() -> new MovieNotFoundByTitle());

        return movieMapper.toResponse(movie);
    }
}
