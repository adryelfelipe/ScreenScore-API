package ctw.screenscoreapi.Module.Avaliations.application.dtos.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateAvaliationRequest(
        @DecimalMax(value = "10.0", inclusive = true, message = "A nota do filme avaliado deve ser no máximo 10.0")
        @DecimalMin(value = "0.0", inclusive = true, message = "A nota do filme avaliado deve ser no mínimo 0.0")
        @Schema(example = "10.0")
        BigDecimal score,

        @Size(min = 2, message = "O comentário da avaliação deve ter no mínimo 2 caracteres")
        @Schema(example = "Filme muito bom! Recomendo")
        String comment
) {}