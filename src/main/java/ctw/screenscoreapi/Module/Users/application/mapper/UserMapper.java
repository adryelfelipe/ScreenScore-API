package ctw.screenscoreapi.Module.Users.application.mapper;

import ctw.screenscoreapi.Module.Users.application.dtos.create.CreateUserRequest;
import ctw.screenscoreapi.Module.Users.application.dtos.get.GetUserResponse;
import ctw.screenscoreapi.Module.Users.domain.entity.UserEntity;
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

    public GetUserResponse toResponse(UserEntity entity) {

        return new GetUserResponse(
                entity.getId(),
                entity.getName(),
                entity.getEmail()
        );
    }
}
