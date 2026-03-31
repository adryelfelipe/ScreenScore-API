package ctw.screenscoreapi.Avaliations.application.dtos.get;

import java.math.BigDecimal;

public record GetAvaliationResponse(Long id, Long userId, Long movieId, BigDecimal score, String comment) {
}
