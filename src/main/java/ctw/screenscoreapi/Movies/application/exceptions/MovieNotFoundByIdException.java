package ctw.screenscoreapi.Movies.application.exceptions;

public class MovieNotFoundByIdException extends MovieApplicationException {
    public MovieNotFoundByIdException(long id) {
        super("Filme não encontrado com o ID: " + id);
    }
}
