package ctw.screenscoreapi.Movies.infra.feign;

import java.util.List;

public class MovieApiResponse {
    private List<MovieApiEntity> results;

    public List<MovieApiEntity> getResults() {
        return results;
    }
}
