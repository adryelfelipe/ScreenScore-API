package ctw.screenscoreapi.Module.Avaliations.application.dtos.create;

import java.math.BigDecimal;

public record CreateAvaliationToEntity(
        Long movieId,
        Long userId,
        BigDecimal score,
        String comment
) {}
