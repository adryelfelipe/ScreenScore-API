package ctw.screenscoreapi.Movies.application.service;

import ctw.screenscoreapi.Movies.application.dtos.create.CreateMovieRequest;
import ctw.screenscoreapi.Movies.application.dtos.get.GetMoviesByTitleResponse;
import ctw.screenscoreapi.Movies.application.dtos.get.GetMovieResponse;
import ctw.screenscoreapi.Movies.application.exceptions.MovieNotFoundByIdException;
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
    public void create(CreateMovieRequest request) {
        String title = request.title();
        Optional<MovieEntity> optionalMovie = movieRepository.findByExactTitle(title);

        if(optionalMovie.isPresent()) {
            throw new MovieTitleAlreadyUsedException(title);
        }

        MovieEntity movie = movieMapper.toEntity(request);
        movieRepository.create(movie);
    }

    public GetMoviesByTitleResponse getExternal(String title) {
        MovieApiResponse movieApiResponse = movieApiClient.search(title, "pt-BR", themoviedbApiKey);
        List<MovieEntity> movies = tmdbMapper.toDomainEntities(movieApiResponse.getResults());

        return tmdbMapper.toResponseEntities(movies);
    }

    public GetMoviesByTitleResponse get(String title) {
        Optional<List<MovieEntity>> optionalMovie = movieRepository.findByLikeTitle(title);
        List<MovieEntity> movieEntities = optionalMovie.orElseThrow(MovieNotFoundByTitle::new);
        List<GetMovieResponse> movieResponses = movieEntities.stream().map(movieMapper::toResponse).toList();

        return new GetMoviesByTitleResponse(movieResponses);
    }

    public void delete(long id) {
        long deletedMovies = movieRepository.delete(id);

        if(deletedMovies == 0) {
            throw new MovieNotFoundByIdException(id);
        }
    }
}
