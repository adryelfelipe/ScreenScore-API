package ctw.screenscoreapi.Avaliations.application.dtos.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateAvaliationRequest(
        @NotNull(message = "O número identificador do filme avaliado é obrigatório")
        @Positive(message = "O número identificador do filme avaliado deve ser positivo")
        @Schema(example = "2")
        Long movieId,

        @NotNull(message = "A nota do filme avaliado é obrigatória")
        @DecimalMax(value = "10.0", inclusive = true, message = "A nota do filme avaliado deve ser no máximo 10.0")
        @DecimalMin(value = "0.0", inclusive = true, message = "A nota do filme avaliado deve ser no mínimo 0.0")
        @Schema(example = "10.0")
        BigDecimal score,

        @Size(min = 2, message = "O comentário da avaliação deve ter no mínimo 2 caracteres")
        @NotBlank(message = "O comentário da avaliação é obrigatório")
        @Schema(example = "Filme muito bom! Recomendo")
        String comment
) {}
