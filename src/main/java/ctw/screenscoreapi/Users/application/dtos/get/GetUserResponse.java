package ctw.screenscoreapi.Users.application.dtos.get;

import io.swagger.v3.oas.annotations.media.Schema;

public record GetUserResponse(
        @Schema(example = "1")
        long id,

        @Schema(example = "Robson")
        String name,

        @Schema(example = "robsoncavalo@gmail.com")
        String email) {
}
