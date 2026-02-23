package ctw.screenscoreapi.Movies.application.dtos.get;

import java.util.List;

public record GetExternalMovieResponse(List<GetMovieResponse> movies) {
}
