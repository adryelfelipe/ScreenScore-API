package ctw.screenscoreapi.Avaliations.application.dtos.create;

public record CreateAvaliationToEntity(
        Long movieId,
        Long userId,
        Double score,
        String comment
) {}
