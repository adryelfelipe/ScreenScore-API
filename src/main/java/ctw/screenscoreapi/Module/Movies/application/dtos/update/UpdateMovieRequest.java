package ctw.screenscoreapi.Module.Movies.application.dtos.update;

import ctw.screenscoreapi.Module.Movies.domain.enums.Genre;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateMovieRequest(
        @Size(min = 2, max = 255, message = "O título do filme deve possuir entre 2 e 255 caracteres")
        @Schema(example = "ScreenScore, o melhor filme de todos!")
        String title,

        @Size(min = 2, max = 4, message = "A língua original deve possui entre 2 e 4 caracteres")
        @Schema(example = "en")
        String originalLanguage,

        @Size(max = 255, message = "O título do filme deve possuir no máximo 255 caracteres")
        @Schema(example = "ScreenScore, the best movie!")
        String originalTitle,

        @Schema(example = "false")
        Boolean adult,

        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "A data deve estar no formato yyyy-MM-dd")
        @Schema(example = "2025-02-24")
        String releaseDate,

        @Size(min = 5, message = "A visão geral do filme deve possuir no mínimo 5 caracteres")
        @Schema(example = "ScreenScore é um filme de ficação científica")
        String overview,

        @Size(min = 1, message = "O gênero do filme é obrigatório")
        @Schema(example =
                """
                [
                    "FICCAO_CIENTIFICA",  
                    "ACAO"
                ]
                """)
        List<Genre> genres
){ }
