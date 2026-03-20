package ctw.screenscoreapi.Auth.application;

import ctw.screenscoreapi.Auth.dtos.LoginRequest;
import ctw.screenscoreapi.Auth.dtos.RegisterRequest;
import ctw.screenscoreapi.Auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {
        long id = authService.register(request);

        return ResponseEntity
                .created(URI.create("/users/" + id))
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest request) {
        authService.login(request);

        return ResponseEntity.ok().build();
    }
}
