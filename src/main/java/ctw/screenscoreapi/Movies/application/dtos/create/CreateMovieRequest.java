package ctw.screenscoreapi.Movies.application.dtos.create;

import ctw.screenscoreapi.Movies.domain.enums.Genre;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateMovieRequest(@NotBlank(message = "O título do filme é obrigatório")
                                 @Size(min = 2, max = 255, message = "O título do filme deve possuir entre 2 e 255 caracteres")
                                 @Schema(example = "ScreenScore, o melhor filme de todos!")
                                 String title,

                                 @Size(min = 2, max = 4, message = "A língua original deve possui entre 2 e 4 caracteres")
                                 @Schema(example = "en")
                                 String originalLanguage,

                                 @Size(max = 255, message = "O título do filme deve possuir no máximo 255 caracteres")
                                 @Schema(example = "ScreenScore, the best movie!")
                                 String originalTitle,

                                 @NotNull(message = "A classificação etária do filme é obrigatória")
                                 @Schema(example = "false")
                                 Boolean adult,

                                 @NotBlank(message = "A data de lançamento do filme é obrigatório")
                                 @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                                         message = "A data deve estar no formato yyyy-MM-dd")
                                 @Schema(example = "2025-02-24")
                                 String releaseDate,

                                 @Schema(description = "Referência da imagem obtida ao se comunicar com ThemovieDB", example = "/referencia-da-imagem-do-themoviedb.jpg")
                                 String posterImage,

                                 @Size(min = 3, message = "A visão geral do filme deve possuir no mínimo 5 caracteres")
                                 @NotBlank(message = "A visão geral do filme é obrigatória")
                                 @Schema(example = "ScreenScore é um filme de ficação científica")
                                 String overview,

                                 @NotNull(message = "O gênero do filme é obrigatório")
                                 @Schema(example = "FICCAO_CIENTIFICA")
                                 List<Genre> genres
) {}
