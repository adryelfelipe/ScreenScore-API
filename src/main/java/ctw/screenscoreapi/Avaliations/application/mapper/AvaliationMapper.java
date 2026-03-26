package ctw.screenscoreapi.Avaliations.application.mapper;

import ctw.screenscoreapi.Avaliations.application.dtos.create.CreateAvaliationToEntity;
import ctw.screenscoreapi.Avaliations.domain.AvaliationEntity;
import org.springframework.stereotype.Component;

@Component
public class AvaliationMapper {
    public AvaliationEntity toEntity(CreateAvaliationToEntity request) {

        return new AvaliationEntity(
                null,
                request.comment(),
                request.score(),
                request.movieId(),
                request.userId()
        );
    }
}
