package ctw.screenscoreapi.Auth.mapper;

import ctw.screenscoreapi.Auth.dtos.RegisterRequest;
import ctw.screenscoreapi.Users.application.dtos.create.CreateUserRequest;
import ctw.screenscoreapi.Users.domain.enums.Role;
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
