package ctw.screenscoreapi.Movies.application.dtos.create;

import ctw.screenscoreapi.Movies.domain.enums.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateMovieRequest(@NotBlank(message = "O título do filme é obrigatório")
                                 @Size(min = 2, max = 255, message = "O título do filme deve possuir entre 2 e 255 caracteres")
                                 String title,

                                 String originalLanguage,

                                 String originalTitle,

                                 @NotNull(message = "A classificação etária do filme é obrigatória")
                                 Boolean adult,

                                 @NotBlank(message = "A data de lançamento do filme é obrigatório")
                                 @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                                         message = "A data deve estar no formato yyyy-MM-dd")
                                 String releaseDate,

                                 String posterImage,

                                 @Size(min = 3, message = "A visão geral do filme deve possuir no mínimo 5 caracteres")
                                 @NotBlank(message = "A visão geral do filme é obrigatória")
                                 String overview,

                                 @NotNull(message = "O gênero do filme é obrigatório")
                                 List<Genre> genres
) {}
