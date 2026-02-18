package ctw.screenscoreapi.Movies.application.service;

import ctw.screenscoreapi.Movies.application.dtos.create.CreateMovieRequest;
import ctw.screenscoreapi.Movies.application.dtos.get.GetExternalMovieRequest;
import ctw.screenscoreapi.Movies.application.dtos.get.GetMovieResponse;
import ctw.screenscoreapi.Movies.application.mapper.MovieMapper;
import ctw.screenscoreapi.Movies.domain.MovieEntity;
import ctw.screenscoreapi.Movies.domain.repository.MovieRepository;
import ctw.screenscoreapi.Movies.infra.feign.MovieApiClient;
import ctw.screenscoreapi.Movies.infra.feign.MovieApiEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class MovieService {
    // Atributos
    private MovieApiClient movieApiClient;
    private MovieMapper movieMapper;
    private MovieRepository movieRepository;

    @Value("${themoviedb.apikey}")
    private String themoviedbApiKey;

    // Construtor
    public MovieService(MovieApiClient movieApiClient, MovieMapper movieMapper, MovieRepository movieRepository) {
        this.movieApiClient = movieApiClient;
        this.movieMapper = movieMapper;
        this.movieRepository = movieRepository;
    }

    // Metodos
    public void create(CreateMovieRequest request) {
        MovieEntity movie = movieMapper.toEntity(request);
        movieRepository.create(movie);
    }

    public GetMovieResponse getExternal(GetExternalMovieRequest request) {
        MovieApiEntity movieApiEntity = movieApiClient.search(request.title(), "pt-BR", themoviedbApiKey);
        MovieEntity movieEntity = movieMapper.toEntity(movieApiEntity);

        return movieMapper.toResponse(movieEntity);
    }
}
