package ctw.screenscoreapi.Users.application.controller;

import ctw.screenscoreapi.Users.application.dtos.CreateUserRequest;
import ctw.screenscoreapi.Users.application.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateUserRequest request) {
        long id = userService.create(request);

        return ResponseEntity
                .created(URI.create("/usuarios/" + id))
                .build();
    }
}
