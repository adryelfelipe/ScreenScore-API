package ctw.screenscoreapi.Movies.application.exceptions;

public class MovieNotFoundByIdException extends MovieNotFoundException {
    public MovieNotFoundByIdException(long id) {
        super("Não foi possível encontrar um filme com o ID: " + id);
    }
}
