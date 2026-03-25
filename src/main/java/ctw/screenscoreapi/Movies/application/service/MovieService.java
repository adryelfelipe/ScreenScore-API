package ctw.screenscoreapi.Movies.application.service;

import ctw.screenscoreapi.Movies.application.dtos.create.CreateMovieRequest;
import ctw.screenscoreapi.Movies.application.dtos.get.GetExternalMovieResponse;
import ctw.screenscoreapi.Movies.application.dtos.get.GetListOfExternalMoviesResponse;
import ctw.screenscoreapi.Movies.application.dtos.get.GetMovieResponse;
import ctw.screenscoreapi.Movies.application.dtos.get.GetListOfMoviesResponse;
import ctw.screenscoreapi.Movies.application.dtos.update.UpdateMovieRequest;
import ctw.screenscoreapi.Movies.application.exceptions.MovieNoContentToUpdateException;
import ctw.screenscoreapi.Movies.application.exceptions.MovieNotFoundByIdException;
import ctw.screenscoreapi.Movies.application.exceptions.MovieTitleAlreadyUsedException;
import ctw.screenscoreapi.Movies.domain.enums.Genre;
import ctw.screenscoreapi.Movies.infra.aws.service.S3Service;
import ctw.screenscoreapi.Movies.infra.themoviedb.feign.models.detailed.DetailedMovieApiEntity;
import ctw.screenscoreapi.Share.exception.categories.NoContentToUpdateException;
import ctw.screenscoreapi.Movies.application.mapper.MovieMapper;
import ctw.screenscoreapi.Movies.domain.MovieEntity;
import ctw.screenscoreapi.Movies.domain.repository.MovieRepository;
import ctw.screenscoreapi.Movies.infra.themoviedb.feign.MovieApiClient;
import ctw.screenscoreapi.Movies.infra.themoviedb.feign.models.MovieApiResponse;
import ctw.screenscoreapi.Movies.infra.themoviedb.mapper.TmdbMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.cfg.MapperBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    // Atributos
    private MovieApiClient movieApiClient;
    private MovieMapper movieMapper;
    private TmdbMapper tmdbMapper;
    private MovieRepository movieRepository;
    private S3Service s3Service;

    @Value("${themoviedb.apikey}")
    private String themoviedbApiKey;

    // Construtor
    public MovieService(MovieApiClient movieApiClient, MovieMapper movieMapper, MovieRepository movieRepository, TmdbMapper tmdbMapper, S3Service s3Service) {
        this.movieApiClient = movieApiClient;
        this.movieMapper = movieMapper;
        this.movieRepository = movieRepository;
        this.tmdbMapper = tmdbMapper;
        this.s3Service = s3Service;
    }

    // Metodos
    public long create(CreateMovieRequest request, MultipartFile file) throws IOException {
        String title = request.title();
        Optional<MovieEntity> optionalMovie = movieRepository.findByExactTitle(title);

        if(optionalMovie.isPresent()) {
            throw new MovieTitleAlreadyUsedException(title);
        }

        String posterKey = s3Service.putObject(file);

        MovieEntity movie = movieMapper.toEntity(request, posterKey);
        return movieRepository.create(movie);
    }

    public GetListOfExternalMoviesResponse getExternalByTitle(String title) {
        MovieApiResponse movieApiResponse = movieApiClient.search(title, "pt-BR", themoviedbApiKey);
        List<MovieEntity> movies = tmdbMapper.toDomainEntities(movieApiResponse.getResults());

        return tmdbMapper.toResponseEntities(movies);
    }

    public GetExternalMovieResponse getExternalById(long id) {
        DetailedMovieApiEntity movieApiEntity = movieApiClient.search(id, "pt-BR", themoviedbApiKey);
        MovieEntity movieEntity = tmdbMapper.toDomainEntity(movieApiEntity);

        return tmdbMapper.toResponseEntity(movieEntity);
    }

    public GetMovieResponse getById(long id) {
        MovieEntity movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundByIdException(id));                                             ;
        String posterUrl = s3Service.getObject(movie.getPosterImage());
        movie.setPosterImage(posterUrl);

        return movieMapper.toResponse(movie);
    }

    public GetListOfMoviesResponse getMovies(String title, List<Genre> genres) {
        if(title != null || genres != null){
            List<MovieEntity> movieEntities = movieRepository.findMovieByFilter(title, genres);
            movieEntities.stream().forEach(m -> m.setPosterImage(s3Service.getObject(m.getPosterImage())));
            List<GetMovieResponse> movieResponses = movieEntities.stream().map(movieMapper::toResponse).toList();

            return new GetListOfMoviesResponse(movieResponses);
        }

        List<MovieEntity> movies = movieRepository.getAllMovies();
        movies.stream().forEach(m -> m.setPosterImage(s3Service.getObject(m.getPosterImage())));
        List<GetMovieResponse> movieResponses = movies.stream().map(movieMapper::toResponse).toList();

        return movieMapper.toResponse(movies);
    }

    public void delete(long id) {
        MovieEntity movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundByIdException(id));
        movieRepository.delete(id);
        String posterKey = movie.getPosterImage();
        s3Service.deleteObject(posterKey);
    }

    public void update(long id, UpdateMovieRequest request) {
        if (request.title() == null &&
                request.originalLanguage() == null &&
                request.originalTitle() == null &&
                request.adult() == null &&
                request.releaseDate() == null &&
                request.posterImage() == null &&
                request.overview() == null &&
                request.genres() == null) {

            throw new MovieNoContentToUpdateException();
        }

        MovieEntity movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundByIdException(id));

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

    public GetListOfMoviesResponse getTop10Movies() {
        // Implement after create the avaliations module
        return getMovies(null ,null);
    }
}
