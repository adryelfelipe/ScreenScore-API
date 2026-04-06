package ctw.screenscoreapi.Module.Auth.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "O nome é obrigatório")
        @Schema(description = "Nome do usuário", example = "Adryel Sapelli")
        String name,

        @Email(message = "O formato do email deve estar válido")
        @NotBlank(message = "O email é obrigatório")
        @Schema(description = "Email do usuário", example = "adryelsapelli@gmail.com")
        String email,

        @Size(min = 8, max = 100, message = "Senha deve ter entre 8 e 100 caracteres")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).+$",
                message = "Senha deve conter pelo menos 1 letra maiúscula, 1 minúscula, 1 número e 1 símbolo"
        )
        @NotBlank(message = "A senha é obrigatória")
        @Schema(description = "Senha do usuário", example = "Abcdefg123@")
        String password
) {}
