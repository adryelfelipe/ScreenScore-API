package ctw.screenscoreapi.Auth.service;

import ctw.screenscoreapi.Auth.dtos.LoginRequest;
import ctw.screenscoreapi.Auth.dtos.RegisterRequest;
import ctw.screenscoreapi.Auth.mapper.AuthMapper;
import ctw.screenscoreapi.Users.application.dtos.create.CreateUserRequest;
import ctw.screenscoreapi.Auth.exception.InvalidCredentialsException;
import ctw.screenscoreapi.Users.application.exception.UserNotFoundByEmailException;
import ctw.screenscoreapi.Users.application.service.UserService;
import ctw.screenscoreapi.Users.domain.entity.UserEntity;
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

    public void login(LoginRequest request) {
        try{
            UserEntity user = userService.getFullUserByEmail(request.email());

            if(!user.getPassword().equals(request.password())) {
                throw new InvalidCredentialsException();
            }
        } catch (UserNotFoundByEmailException e) {
            throw new InvalidCredentialsException();
        }
    }
}
