package ctw.screenscoreapi.Module.Movies.infra.themoviedb.feign;

import ctw.screenscoreapi.Module.Movies.infra.themoviedb.feign.models.MovieApiResponse;
import ctw.screenscoreapi.Module.Movies.infra.themoviedb.feign.models.detailed.DetailedMovieApiEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url="${themoviedb.api.url}", name = "FilmeApiClient")
public interface MovieApiClient {

    @GetMapping("/search/movie")
    MovieApiResponse search(@RequestParam String query, @RequestParam String language, @RequestHeader("Authorization") String api_key);

    @GetMapping("/movie/{id}")
    DetailedMovieApiEntity search(@PathVariable long id, @RequestParam String language, @RequestHeader("Authorization") String api_key);
}
