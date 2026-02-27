package ctw.screenscoreapi.Movies.application.dtos.get;

import ctw.screenscoreapi.Movies.domain.MovieEntity;
import ctw.screenscoreapi.Movies.domain.enums.Genre;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record GetMovieResponse(
        @Schema(example = "ScreenScore, batalha nas esstrelas")
        String title,

        @Schema(example = "eu")
        String originalLanguage,

        @Schema(example = "ScreenScore, battle in the Stars")
        String originalTitle,

        @Schema(example = "false")
        Boolean adult,

        @Schema(example = "2026-02-26")
        String releaseDate,

        @Schema(example = "/caminho-da-imagem.jpg")
        String posterImage,

        @Schema(example = "ScreenScore é a melhor API para gestão de filmes!")
        String overview,

        @Schema(example =
                """
                [
                    "ACAO", 
                    "AVENTURA"
                ]
                """)
        List<Genre> genres
)  {}

