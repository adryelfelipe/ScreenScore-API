package ctw.screenscoreapi.Users.application.service;

import ctw.screenscoreapi.Users.application.dtos.create.CreateUserRequest;
import ctw.screenscoreapi.Users.application.dtos.get.GetUserResponse;
import ctw.screenscoreapi.Users.application.exception.UserNotFoundById;
import ctw.screenscoreapi.Users.application.mapper.UserMapper;
import ctw.screenscoreapi.Users.domain.entity.UserEntity;
import ctw.screenscoreapi.Users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserMapper userMapper;
    private UserRepository userRepository;

    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public long create(CreateUserRequest request) {
        UserEntity user = userMapper.toEntity(request);

        return userRepository.create(user);
    }

    public GetUserResponse get(long id) {
        Optional<UserEntity> optionalUser = userRepository.getById(id);
        UserEntity user = optionalUser.orElseThrow(() -> new UserNotFoundById(id));

        return userMapper.toResponse(user);
    }
}
