package ctw.screenscoreapi.Movies.application.service;

import ctw.screenscoreapi.Movies.application.dtos.create.CreateMovieRequest;
import ctw.screenscoreapi.Movies.application.dtos.get.GetExternalMovieRequest;
import ctw.screenscoreapi.Movies.application.dtos.get.GetMovieResponse;
import ctw.screenscoreapi.Movies.application.exceptions.MovieTitleAlreadyUsed;
import ctw.screenscoreapi.Movies.application.mapper.MovieMapper;
import ctw.screenscoreapi.Movies.domain.MovieEntity;
import ctw.screenscoreapi.Movies.domain.repository.MovieRepository;
import ctw.screenscoreapi.Movies.infra.feign.MovieApiClient;
import ctw.screenscoreapi.Movies.infra.feign.MovieApiEntity;
import ctw.screenscoreapi.Movies.infra.feign.MovieApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.swing.text.html.Option;
import java.util.Optional;

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
        String title = request.title();
        Optional<MovieEntity> optionalMovie = movieRepository.findByTitle(title);

        if(optionalMovie.isPresent()) {
            throw new MovieTitleAlreadyUsed(title);
        }

        MovieEntity movie = movieMapper.toEntity(request);
        movieRepository.create(movie);
    }

    public GetMovieResponse getExternal(GetExternalMovieRequest request) {
        MovieApiResponse movieApiResponse = movieApiClient.search(request.title(), "pt-BR", themoviedbApiKey);

        return movieMapper.toResponse(movieApiResponse.getResults());
    }
}
