package ctw.screenscoreapi.Module.Auth.controller;

import ctw.screenscoreapi.Module.Auth.dtos.LoginRequest;
import ctw.screenscoreapi.Module.Auth.dtos.RegisterRequest;
import ctw.screenscoreapi.Module.Auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints para gerenciamento de autenticação")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Registra um novo usuário no sistema",
            description = "Registra um novo usuário no sistema caso esteja válido e não exista nenhum outro com o mesmo email"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário registrado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    ref = "#/components/responses/400"
            ),
            @ApiResponse(
                    responseCode =  "409",
                    ref = "#/components/responses/409"
            ),
            @ApiResponse(
                    responseCode = "500",
                    ref = "#/components/responses/500"
            )
    })
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {
        long id = authService.register(request);

        return ResponseEntity
                .created(URI.create("/users/" + id))
                .build();
    }

    @Operation(
            summary = "Autentica usuário no sistema",
            description = "Autentica usuário no sistema, permitindo que ele usufrua das demais funcionalidades disponíveis"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário autenticado com sucesso!"
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
                    responseCode = "500",
                    ref = "#/components/responses/500"
            )
    })
    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest request) {
        authService.login(request);

        return ResponseEntity.ok().build();
    }
}
