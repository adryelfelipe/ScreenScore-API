package ctw.screenscoreapi.Users.application.dtos.create;

import ctw.screenscoreapi.Users.domain.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record CreateUserRequest(
        @NotBlank(message = "O nome é obrigatório")
        @Schema(example = "Robson Cavalo")
        String name,

        @Email(message = "O formato do email deve estar válido")
        @NotBlank(message = "O email é obrigatório")
        @Schema(example = "robsoncavalo@gmail.com")
        String email,

        @Size(min = 8, max = 100, message = "Senha deve ter entre 8 e 100 caracteres")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).+$",
                message = "Senha deve conter pelo menos 1 letra maiúscula, 1 minúscula, 1 número e 1 símbolo"
        )
        @NotBlank(message = "A senha é obrigatória")
        @Schema(example = "Abcdefg@123")
        String password,

        @NotNull(message = "O tipo de usuário é obrigatório")
        @Schema(example = "CLIENTE")
        Role role)
{}
