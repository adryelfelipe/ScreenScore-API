package ctw.screenscoreapi.Movies.application.exceptions;

public class MovieDataAlreadyUsedException extends RuntimeException {
    public MovieDataAlreadyUsedException(String message) {
        super(message);
    }
}
