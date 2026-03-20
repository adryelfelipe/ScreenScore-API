package ctw.screenscoreapi.Auth.service;

import ctw.screenscoreapi.Auth.dtos.RegisterRequest;
import ctw.screenscoreapi.Auth.mapper.AuthMapper;
import ctw.screenscoreapi.Users.application.dtos.create.CreateUserRequest;
import ctw.screenscoreapi.Users.application.service.UserService;
import ctw.screenscoreapi.Users.domain.enums.Role;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private UserService userService;
    private AuthMapper authMapper;

    public AuthService(UserService userService, AuthMapper authMapper) {
        this.userService = userService;
        this.authMapper = authMapper;
    }

    public long register(RegisterRequest request) {
        CreateUserRequest createRequest = authMapper.toCreateUserRequest(request);

        return userService.create(createRequest);
    }
}
