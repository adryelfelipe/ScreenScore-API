package ctw.screenscoreapi.Movies.application;

import ctw.screenscoreapi.Module.Movies.application.dtos.create.CreateMovieRequest;
import ctw.screenscoreapi.Module.Movies.application.dtos.get.GetMovieResponse;
import ctw.screenscoreapi.Module.Movies.application.exceptions.MovieTitleAlreadyUsedException;
import ctw.screenscoreapi.Module.Movies.application.mapper.MovieMapper;
import ctw.screenscoreapi.Module.Movies.application.service.MovieService;
import ctw.screenscoreapi.Module.Movies.domain.entity.MovieEntity;
import ctw.screenscoreapi.Module.Movies.domain.enums.Genre;
import ctw.screenscoreapi.Module.Movies.domain.repository.MovieRepository;
import ctw.screenscoreapi.Module.Movies.infra.aws.service.S3Service;
import ctw.screenscoreapi.Module.Movies.infra.themoviedb.feign.MovieApiClient;
import ctw.screenscoreapi.Module.Movies.infra.themoviedb.mapper.TmdbMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {
    @Mock
    MovieApiClient movieApiClient;

    @Mock
    MovieMapper movieMapper;

    @Mock
    TmdbMapper tmdbMapper;

    @Mock
    MovieRepository movieRepository;

    @Mock
    S3Service s3Service;

    MultipartFile file;

    @InjectMocks
    MovieService movieService;

    @BeforeEach
    void setUp() {
        file = mock(MultipartFile.class);
    }

    @Test
    @DisplayName("Should create a movie and return its ID successfully")
    public void shouldCreateMovieSuccessfully() throws IOException {
        // Arrange
        String posterKey = "123";
        String title =  "ScreenScore, o melhor filme de todos!";
        String releaseDate = "2025-02-24";
        String originalLanguage = "en";
        String originalTitle = "ScreenScore, the best movie!";
        Boolean adult  = false;
        String overview = "ScreenScore é um filme de ficação científica";
        List<Genre> genres = List.of(Genre.FICCAO_CIENTIFICA, Genre.ACAO);
        MovieEntity movie = new MovieEntity(1L, posterKey, releaseDate, adult, originalTitle, originalLanguage, title, overview, genres, null, null);
        CreateMovieRequest request = new CreateMovieRequest(title, originalLanguage, originalTitle, adult, releaseDate, overview, genres);

        when(movieRepository.findByExactTitle(request.title())).thenReturn(Optional.empty());
        when(s3Service.putObject(file)).thenReturn(posterKey);
        when(movieMapper.toEntity(request, posterKey)).thenReturn(movie);
        when(movieRepository.create(movie)).thenReturn(movie.getId());

        // Act
        Long movieId = movieService.create(request, file);

        // Assert
        verify(movieRepository).findByExactTitle(request.title());
        verify(s3Service).putObject(file);
        verify(movieMapper).toEntity(request, posterKey);
        verify(movieRepository).create(movie);
        assertThat(movieId).isEqualTo(movie.getId());
    }

    @Test
    @DisplayName("Should throw MovieTitleAlreadyUsedException when the movie title is already used")
    public void shouldThrowMovieTitleAlreadyUsedExceptionWhenTheMovieTitleIsAlreadyUsed() throws IOException {
        // Arrange
        String posterKey = "123";
        String title =  "ScreenScore, o melhor filme de todos!";
        String releaseDate = "2025-02-24";
        String originalLanguage = "en";
        String originalTitle = "ScreenScore, the best movie!";
        Boolean adult  = false;
        String overview = "ScreenScore é um filme de ficação científica";
        List<Genre> genres = List.of(Genre.FICCAO_CIENTIFICA, Genre.ACAO);
        MovieEntity movie = new MovieEntity(1L, posterKey, releaseDate, adult, originalTitle, originalLanguage, title, overview, genres, null, null);
        CreateMovieRequest request = new CreateMovieRequest(title, originalLanguage, originalTitle, adult, releaseDate, overview, genres);
        when(movieRepository.findByExactTitle(request.title())).thenReturn(Optional.of(movie));

        // Asserts + Act
        assertThrows(MovieTitleAlreadyUsedException.class, () -> movieService.create(request, file));
        verify(movieRepository, never()).create(any());
        verify(movieRepository).findByExactTitle(request.title());
        verifyNoInteractions(movieMapper);
        verifyNoInteractions(s3Service);
    }

    @Test
    @DisplayName("Should return a movie with presigned post url")
    public void shouldReturnMovieWithPresignedPostUrlSuccessfully() {
        // Arrange
        String presignedUrl = "test";
        String posterKey = "123";
        String title =  "ScreenScore, o melhor filme de todos!";
        String releaseDate = "2025-02-24";
        String originalLanguage = "en";
        String originalTitle = "ScreenScore, the best movie!";
        Boolean adult  = false;
        String overview = "ScreenScore é um filme de ficação científica";
        List<Genre> genres = List.of(Genre.FICCAO_CIENTIFICA, Genre.ACAO);
        MovieEntity movie = new MovieEntity(1L, posterKey, releaseDate, adult, originalTitle, originalLanguage, title, overview, genres, null, null);
        when(movieRepository.findById(movie.getId())).thenReturn(Optional.of(movie));
        when(s3Service.getPresignedUrl(movie.getPosterImage())).thenReturn(presignedUrl);
        when(movieMapper.toResponse(movie)).thenReturn(new GetMovieResponse(movie.getId(), movie.getTitle(), movie.getOriginalLanguage(), movie.getOriginalTitle(), movie.isAdult(), movie.getReleaseDate(), presignedUrl, movie.getOverview(), movie.getGenres(), movie.getAvaliationsIds(), movie.getAverageScore()));

        // Act
        GetMovieResponse response = movieService.getById(movie.getId());

        // Asserts
        verify(movieRepository).findById(movie.getId());
        verify(s3Service).getPresignedUrl(posterKey);
        assertThat(movie.getPosterImage()).isEqualTo(response.posterImage());
    }
}
