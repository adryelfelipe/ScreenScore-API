package ctw.screenscoreapi.Movies.application.exceptions;

public class MovieNotFoundByTitle extends MovieApplicationException {
    public MovieNotFoundByTitle() {
        super("Filme não encontrado");
    }
}
