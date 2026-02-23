package ctw.screenscoreapi.Movies.application.dtos.get;

import jakarta.validation.constraints.NotBlank;

public record GetExternalMovieRequest(@NotBlank(message = "O título do filme é obrigatório") String title) {
}
