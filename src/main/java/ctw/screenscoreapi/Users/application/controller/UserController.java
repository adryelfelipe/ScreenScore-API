package ctw.screenscoreapi.Users.application.controller;

import ctw.screenscoreapi.Share.aop.authentication.ToAuthenticate;
import ctw.screenscoreapi.Share.aop.authorization.ToAuthorize;
import ctw.screenscoreapi.Users.application.dtos.create.CreateUserRequest;
import ctw.screenscoreapi.Users.application.dtos.get.GetListOfUsersResponse;
import ctw.screenscoreapi.Users.application.dtos.get.GetUserResponse;
import ctw.screenscoreapi.Users.application.dtos.update.UpdateUserRequest;
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
import org.springframework.http.MediaType;
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
    @ToAuthenticate
    @ToAuthorize
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
    @ToAuthenticate
    @ToAuthorize
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
    @ToAuthenticate
    @ToAuthorize
    public ResponseEntity<Void> deleteById(
            @Positive(message = "O ID deve ser um número positivo")
            @Parameter(example = "1", description = "Número identificar do usuário")
            @PathVariable long id) {
        userService.deleteById(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @Operation(
            summary = "Retorna todos os usuários do sistema",
            description = "Retorna todos os usuários cadastrados no sistema, caso exista algum"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuários retornada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetListOfUsersResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    ref = "#/components/responses/401"
            ),
            @ApiResponse(
                    responseCode = "500",
                    ref = "#/components/responses/500"
            )
    })
    @GetMapping
    @ToAuthenticate
    @ToAuthorize
    public ResponseEntity<GetListOfUsersResponse> getAll() {
        GetListOfUsersResponse users = userService.getAll();

        return ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Atualiza um usuário cadastrado no sistema a partir do ID",
            description =
            """
            Atualiza os dados de um usuário previamente cadastrado no sistema a partir do ID.
            Você deve enviar pelo menos **um parâmetro** para atualizar.
           
            Os campos disponíveis são:
            
            - **name**: novo nome do usuário (opcional)
            - **email**: novo email do usuário (opcional, mas deve ser válido se fornecido)
            - **password**: nova senha (opcional, deve ter entre 8 e 100 caracteres, conter pelo menos 1 letra maiúscula, 1 minúscula, 1 número e 1 símbolo)
            - **role**: novo papel/permissão do usuário (opcional)
            
            Campos não enviados permanecerão inalterados.
            A requisição falhará se nenhum parâmetro for enviado ou se algum campo enviado não atender às regras de validação.
            """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Usuário alterado com sucesso"
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
                    responseCode = "409",
                    ref = "#/components/responses/409"
            ),
            @ApiResponse(
                    responseCode = "422",
                    ref = "#/components/responses/422"
            ),
            @ApiResponse(
                    responseCode = "500",
                    ref = "#/components/responses/500"
            )
    })
    @PutMapping("/{id}")
    @ToAuthenticate
    @ToAuthorize
    public ResponseEntity<Void> update(
            @Positive(message = "O número identificador de usuário deve ser positivo")
            @PathVariable
            long id,

            @Valid
            @RequestBody
            UpdateUserRequest request) {

        userService.update(id, request);

        return ResponseEntity
                .noContent()
                .build();
    }
}
