package ctw.screenscoreapi.Movies.infra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url="${themoviedb.api.url}", name = "FilmeApiClient")
public interface MovieApiClient {

    @GetMapping("/search/movie")
    MovieApiEntity search(@RequestParam String query, @RequestParam String language, @RequestParam String api_key);
}
