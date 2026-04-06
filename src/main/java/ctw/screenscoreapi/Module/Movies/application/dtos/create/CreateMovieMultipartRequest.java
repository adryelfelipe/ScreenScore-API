package ctw.screenscoreapi.Module.Movies.application.dtos.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record   CreateMovieMultipartRequest(
        @NotNull(message = "Os dados do filme são obrigatórios")
        @Schema(description = "Dados do filme")
        CreateMovieRequest data,

        @NotNull(message = "O poster do filme é obrigatório")
        @Schema(description = "Poster do filme")
        MultipartFile file
) {}


