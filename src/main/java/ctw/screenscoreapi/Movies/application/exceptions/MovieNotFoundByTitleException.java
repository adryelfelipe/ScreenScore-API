package ctw.screenscoreapi.Movies.application.exceptions;

public class MovieNotFoundByTitleException extends MovieNotFoundException {
    public MovieNotFoundByTitleException() {
        super("Não foi possível identificar um filme com este título");
    }
}
