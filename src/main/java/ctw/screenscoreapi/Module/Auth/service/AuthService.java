package ctw.screenscoreapi.Module.Auth.service;

import ctw.screenscoreapi.Module.Auth.dtos.LoginRequest;
import ctw.screenscoreapi.Module.Auth.dtos.RegisterRequest;
import ctw.screenscoreapi.Module.Auth.mapper.AuthMapper;
import ctw.screenscoreapi.Module.Users.application.dtos.create.CreateUserRequest;
import ctw.screenscoreapi.Module.Auth.exception.InvalidCredentialsException;
import ctw.screenscoreapi.Module.Users.application.exception.UserNotFoundByEmailException;
import ctw.screenscoreapi.Module.Users.application.service.UserService;
import ctw.screenscoreapi.Module.Users.domain.entity.UserEntity;
import ctw.screenscoreapi.Module.Users.infra.session.UserSession;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private UserService userService;
    private AuthMapper authMapper;
    private UserSession userSession;

    public AuthService(UserService userService, AuthMapper authMapper, UserSession userSession) {
        this.userService = userService;
        this.authMapper = authMapper;
        this.userSession = userSession;
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

            userSession.setUserId(user.getId());
        } catch (UserNotFoundByEmailException e) {
            throw new InvalidCredentialsException();
        }
    }
}
