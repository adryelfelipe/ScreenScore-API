package ctw.screenscoreapi.Movies.application.dtos.get;

public record GetMovieResponse(
        Long id,
        String title,
        String originalLanguage,
        String originalTitle,
        boolean adult,
        String releaseDate,
        String posterImage
)  {}

