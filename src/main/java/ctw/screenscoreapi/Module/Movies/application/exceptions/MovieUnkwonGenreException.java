package ctw.screenscoreapi.Module.Movies.application.exceptions;

public class MovieUnkwonGenreException extends MovieApplicationException {
    public MovieUnkwonGenreException(int id) {
        super("Gênero de filme não encontrado com o id: " + id);
    }
}
