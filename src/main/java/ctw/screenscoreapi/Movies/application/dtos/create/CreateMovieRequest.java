package ctw.screenscoreapi.Movies.application.dtos.create;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateMovieRequest(@NotBlank(message = "O título do filme é obrigatório")
                                 String title,

                                 String originalLanguage,

                                 String originalTitle,

                                 boolean adult,

                                 String releaseDate,

                                 String posterImage,

                                 String overview,

                                 List<Integer> genreIds
) {}
