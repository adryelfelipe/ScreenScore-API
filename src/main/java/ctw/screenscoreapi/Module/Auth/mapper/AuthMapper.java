package ctw.screenscoreapi.Module.Auth.mapper;

import ctw.screenscoreapi.Module.Auth.dtos.RegisterRequest;
import ctw.screenscoreapi.Module.Users.application.dtos.create.CreateUserRequest;
import ctw.screenscoreapi.Module.Users.domain.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    public CreateUserRequest toCreateUserRequest(RegisterRequest request) {

        return new CreateUserRequest(
                request.name(),
                request.email(),
                request.password(),
                Role.CLIENTE
        );
    }
}
