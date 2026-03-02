package ctw.screenscoreapi.Movies.application.exceptions;

public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException(String message) {
        super(message);
    }
}
