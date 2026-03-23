package ctw.screenscoreapi.Users.application.controller;

import ctw.screenscoreapi.Users.application.dtos.create.CreateUserRequest;
import ctw.screenscoreapi.Users.application.dtos.get.GetUserResponse;
import ctw.screenscoreapi.Users.application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Endpoints para gerenciamento de usuários")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Cria usuário no sistema",
            description = "Cria um novo usuário no sistema caso não exista nenhum outro com o mesmo email"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário criado com sucesso!"
            ),
            @ApiResponse(
                    responseCode = "400",
                    ref = "#/components/responses/400"
            ),
            @ApiResponse(
                    responseCode = "401",
                    ref = "#/components/responses/401"
            ),
            @ApiResponse(
                    responseCode = "409",
                    ref = "#/components/responses/409"
            ),
            @ApiResponse(
                    responseCode = "500",
                    ref = "#/components/responses/500"
            )
    })
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateUserRequest request) {
        long id = userService.create(request);

        return ResponseEntity
                .created(URI.create("/usuarios/" + id))
                .build();
    }

    @Operation(
            summary = "Retorna um usuário cadastrado a partir do ID",
            description = "Retorna os detalhes de um usuário previamente cadastrado no sistema, com base no ID informado na URL."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetUserResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    ref = "#/components/responses/400"
            ),
            @ApiResponse(
                    responseCode = "401",
                    ref = "#/components/responses/401"
            ),
            @ApiResponse(
                    responseCode = "404",
                    ref = "#/components/responses/404"
            ),
            @ApiResponse(
                    responseCode = "500",
                    ref = "#/components/responses/500"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getById(
            @Positive(message = "O ID deve ser um número positivo")
            @Parameter(description = "Número identificador do usuário", example = "1")
            @PathVariable long id) {
        GetUserResponse response = userService.getById(id);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Deleta usuários através do ID",
            description = "Deleta usuários cadastrados no sistema a partir do ID enviado como parâmetro na URL"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Usuário deletado com sucesso!"
            ),
            @ApiResponse(
                    responseCode = "401",
                    ref = "#/components/responses/401"
            ),
            @ApiResponse(
                    responseCode = "404",
                    ref = "#/components/responses/404"
            ),
            @ApiResponse(
                    responseCode = "500",
                    ref = "#/components/responses/500"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Positive(message = "O ID deve ser um número positivo")
            @Parameter(example = "1", description = "Número identificar do usuário")
            @PathVariable long id) {
        userService.deleteById(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
