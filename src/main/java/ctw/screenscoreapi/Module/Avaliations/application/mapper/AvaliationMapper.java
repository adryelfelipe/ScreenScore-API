package ctw.screenscoreapi.Module.Avaliations.application.mapper;

import ctw.screenscoreapi.Module.Avaliations.application.dtos.create.CreateAvaliationToEntity;
import ctw.screenscoreapi.Module.Avaliations.application.dtos.get.GetAvaliationResponse;
import ctw.screenscoreapi.Module.Avaliations.application.dtos.get.GetListOfAvaliationResponse;
import ctw.screenscoreapi.Module.Avaliations.domain.entity.AvaliationEntity;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public GetAvaliationResponse toResponse(AvaliationEntity avaliation) {

        return new GetAvaliationResponse(
                avaliation.getId(),
                avaliation.getUserId(),
                avaliation.getMovieId(),
                avaliation.getScore(),
                avaliation.getComment()
        );
    }

    public GetListOfAvaliationResponse toResponse(List<GetAvaliationResponse> avaliations) {

        return new GetListOfAvaliationResponse(avaliations);
    }
}
