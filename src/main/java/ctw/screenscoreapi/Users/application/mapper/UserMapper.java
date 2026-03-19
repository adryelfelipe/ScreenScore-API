package ctw.screenscoreapi.Users.application.mapper;

import ctw.screenscoreapi.Users.application.dtos.CreateUserRequest;
import ctw.screenscoreapi.Users.domain.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity toEntity(CreateUserRequest request) {

        return new UserEntity(
                null,
                request.password(),
                request.name(),
                request.email(),
                request.role()
        );
    }
}
