package ctw.screenscoreapi.Movies.application.dtos.get;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record GetExternalMovieRequest(
        @NotBlank(message = "O título do filme é obrigatório")
        @Schema(example = "Minions")
        String title
) {}
