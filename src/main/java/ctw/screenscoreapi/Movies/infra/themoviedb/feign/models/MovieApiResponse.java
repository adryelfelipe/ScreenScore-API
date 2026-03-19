package ctw.screenscoreapi.Movies.infra.themoviedb.feign.models;

import java.util.List;

public class MovieApiResponse {
    private List<MovieApiEntity> results;

    public List<MovieApiEntity> getResults() {
        return results;
    }
}
