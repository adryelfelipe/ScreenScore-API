package ctw.screenscoreapi.Users.application.controller;

import ctw.screenscoreapi.Users.application.dtos.create.CreateUserRequest;
import ctw.screenscoreapi.Users.application.dtos.get.GetUserResponse;
import ctw.screenscoreapi.Users.application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getById(@PathVariable long id) {
        GetUserResponse response = userService.getById(id);

        return ResponseEntity.ok(response);
    }
}
