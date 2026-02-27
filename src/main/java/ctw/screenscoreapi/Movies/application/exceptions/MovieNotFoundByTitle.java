package ctw.screenscoreapi.Movies.application.exceptions;

public class MovieNotFoundByTitle extends MovieApplicationException {
    public MovieNotFoundByTitle() {
        super("Não foi possível identificar um filme com este título");
    }
}
