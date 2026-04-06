package ctw.screenscoreapi.Module.Movies.infra.themoviedb.feign;

public class TmdbUnkwonGenreException extends RuntimeException {
    public TmdbUnkwonGenreException(int id) {
        super("Gênero de TMDB desconhecido com o id: " + id);
    }
}
