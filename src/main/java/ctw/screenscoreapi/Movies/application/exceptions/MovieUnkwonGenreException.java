package ctw.screenscoreapi.Movies.application.exceptions;

public class MovieUnkwonGenreException extends RuntimeException {
    public MovieUnkwonGenreException(int id) {
        super("Gênero de filme não encontrado com o id: " + id);
    }
}
