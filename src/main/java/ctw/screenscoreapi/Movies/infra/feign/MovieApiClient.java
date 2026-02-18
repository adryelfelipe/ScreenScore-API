package ctw.screenscoreapi.Movies.infra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url="${themoviedb.api.url}", name = "FilmeApiClient")
public interface MovieApiClient {

    @GetMapping("/search/movie")
    MovieApiResponse search(@RequestParam String query, @RequestParam String language, @RequestHeader("Authorization") String api_key);
}
