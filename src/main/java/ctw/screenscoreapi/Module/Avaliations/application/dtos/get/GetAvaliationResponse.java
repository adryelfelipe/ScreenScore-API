package ctw.screenscoreapi.Module.Avaliations.application.dtos.get;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record GetAvaliationResponse(
        @Schema(example = "1")
        Long id,

        @Schema(example = "2")
        Long userId,

        @Schema(example = "3")
        Long movieId,

        @Schema(example = "4.4")
        BigDecimal score,

        @Schema(example = "Não gostei muito do filme. Deslike!")
        String comment) {
}
