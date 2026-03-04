package ctw.screenscoreapi.Movies.application.exceptions;

public class MovieDataAlreadyUsedException extends MovieApplicationException {
    public MovieDataAlreadyUsedException(String message) {
        super(message);
    }
}
