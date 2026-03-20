package ctw.screenscoreapi.Users.application.service;

import ctw.screenscoreapi.Users.application.dtos.create.CreateUserRequest;
import ctw.screenscoreapi.Users.application.dtos.get.GetUserResponse;
import ctw.screenscoreapi.Users.application.exception.UserEmailAlreadyUsed;
import ctw.screenscoreapi.Users.application.exception.UserNotFoundByEmail;
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
        Optional<UserEntity> optionalUser = userRepository.findByEmail(request.email());

        optionalUser.ifPresent(u -> {
            throw new UserEmailAlreadyUsed();
        });

        UserEntity user = userMapper.toEntity(request);

        return userRepository.create(user);
    }

    public GetUserResponse getById(long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        UserEntity user = optionalUser.orElseThrow(() -> new UserNotFoundById(id));

        return userMapper.toResponse(user);
    }

    public GetUserResponse getByEmail(String email) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        UserEntity user = optionalUser.orElseThrow(() -> new UserNotFoundByEmail(email));

        return userMapper.toResponse(user);
    }

    public UserEntity getFullUserByEmail(String email) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);

        return optionalUser.orElseThrow(() -> new UserNotFoundByEmail(email));
    }
}
