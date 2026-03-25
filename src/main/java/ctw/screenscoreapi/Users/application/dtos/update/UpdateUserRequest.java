package ctw.screenscoreapi.Users.application.dtos.update;

import ctw.screenscoreapi.Users.domain.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record UpdateUserRequest(
        @Size(min = 1, message = "O email é obrigatório")
        @Schema(example = "Robson Cavalo")
        String name,

        @Size(min = 1, message = "O email é obrigatório")
        @Email(message = "O formato do email deve estar válido")
        @Schema(example = "robsoncavalo@gmail.com")
        String email,

        @Size(min = 8, max = 100, message = "Senha deve ter entre 8 e 100 caracteres")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).+$",
                message = "Senha deve conter pelo menos 1 letra maiúscula, 1 minúscula, 1 número e 1 símbolo"
        )
        @Schema(example = "Abcdefg@123")
        String password,

        @Schema(example = "CLIENTE")
        Role role)
{}

