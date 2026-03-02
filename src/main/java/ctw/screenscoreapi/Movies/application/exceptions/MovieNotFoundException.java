package ctw.screenscoreapi.Movies.application.exceptions;

public class MovieNotFoundException extends MovieApplicationException {
    public MovieNotFoundException(String message) {
        super(message);
    }
}
