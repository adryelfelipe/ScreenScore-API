package ctw.screenscoreapi.Users.application.service;

import ctw.screenscoreapi.Users.application.dtos.create.CreateUserRequest;
import ctw.screenscoreapi.Users.application.dtos.get.GetListOfUsersResponse;
import ctw.screenscoreapi.Users.application.dtos.get.GetUserResponse;
import ctw.screenscoreapi.Users.application.dtos.update.UpdateUserRequest;
import ctw.screenscoreapi.Users.application.exception.UserEmailAlreadyUsed;
import ctw.screenscoreapi.Users.application.exception.UserNoContentsToUpdate;
import ctw.screenscoreapi.Users.application.exception.UserNotFoundByEmail;
import ctw.screenscoreapi.Users.application.exception.UserNotFoundById;
import ctw.screenscoreapi.Users.application.mapper.UserMapper;
import ctw.screenscoreapi.Users.domain.entity.UserEntity;
import ctw.screenscoreapi.Users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void deleteById(long id) {
        long affectedUsers = userRepository.deleteById(id);

        if(affectedUsers < 1) {
            throw new UserNotFoundById(id);
        }
    }

    public GetListOfUsersResponse getAll() {
        List<UserEntity> users = userRepository.getAll();
        List<GetUserResponse> usersResponse = users
                .stream()
                .map(userMapper::toResponse)
                .toList();

        return new GetListOfUsersResponse(
                usersResponse
        );
    }

    public void update(long id, UpdateUserRequest request) {
        if(request.email() == null && request.name() == null && request.password() == null && request.role() == null) {
            throw new UserNoContentsToUpdate();
        }

        UserEntity userById = userRepository.findById(id).orElseThrow(() -> new UserNotFoundById(id));

        if(request.name() != null) {
            userById.setName(request.name());
        }

        if(request.email() != null) {
            if(userRepository.findByEmail(request.email()).isPresent()) {
                throw new UserEmailAlreadyUsed();
            }

            userById.setEmail(request.email());
        }

        if(request.password() != null) {
            userById.setPassword(request.password());
        }

        if(request.role() != null) {
            userById.setRole(request.role());
        }

        userRepository.update(userById);
    }
}
