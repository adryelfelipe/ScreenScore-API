package ctw.screenscoreapi.Users.application.controller;

import ctw.screenscoreapi.Users.application.dtos.create.CreateUserRequest;
import ctw.screenscoreapi.Users.application.dtos.get.GetUserResponse;
import ctw.screenscoreapi.Users.application.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getById(@PathVariable long id) {
        GetUserResponse response = userService.getById(id);

        return ResponseEntity.ok(response);
    }
}
