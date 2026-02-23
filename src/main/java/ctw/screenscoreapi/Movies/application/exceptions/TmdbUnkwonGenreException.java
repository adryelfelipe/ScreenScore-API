package ctw.screenscoreapi.Movies.application.exceptions;

public class TmdbUnkwonGenreException extends RuntimeException {
    public TmdbUnkwonGenreException(int id) {
        super("Gênero de TMDB desconhecido com o id: " + id);
    }
}
