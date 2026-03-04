package ctw.screenscoreapi.Movies.infra.feign;

public class TmdbUnkwonGenreException extends RuntimeException {
    public TmdbUnkwonGenreException(int id) {
        super("Gênero de TMDB desconhecido com o id: " + id);
    }
}
