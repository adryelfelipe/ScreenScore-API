package ctw.screenscoreapi.Module.Avaliations.application.dtos.get;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record GetListOfAvaliationResponse(
        @Schema(example =
                """
                [
                        {
                          "id": 1,
                          "userId": 2,
                          "movieId": 3,
                          "score": 4.4,
                          "comment": "Não gostei muito do filme. Deslike!"
                        },
                        {
                                  "id": 4,
                                  "userId": 1,
                                  "movieId": 6,
                                  "score": 9.7,
                                  "comment": "Amei esse filme! Like de mais!"
                        }
                ]
                """)
        List<GetAvaliationResponse> avaliations) {
}
