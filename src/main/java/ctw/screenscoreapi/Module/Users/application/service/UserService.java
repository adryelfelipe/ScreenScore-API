package ctw.screenscoreapi.Module.Users.application.service;

import ctw.screenscoreapi.Module.Users.application.dtos.create.CreateUserRequest;
import ctw.screenscoreapi.Module.Users.application.dtos.get.GetListOfUsersResponse;
import ctw.screenscoreapi.Module.Users.application.dtos.get.GetUserResponse;
import ctw.screenscoreapi.Module.Users.application.dtos.update.UpdateUserRequest;
import ctw.screenscoreapi.Module.Users.application.exception.UserEmailAlreadyUsedException;
import ctw.screenscoreapi.Module.Users.application.exception.UserNoContentsToUpdateException;
import ctw.screenscoreapi.Module.Users.application.exception.UserNotFoundByEmailException;
import ctw.screenscoreapi.Module.Users.application.exception.UserNotFoundByIdException;
import ctw.screenscoreapi.Module.Users.application.mapper.UserMapper;
import ctw.screenscoreapi.Module.Users.domain.entity.UserEntity;
import ctw.screenscoreapi.Module.Users.domain.repository.UserRepository;
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
            throw new UserEmailAlreadyUsedException();
        });

        UserEntity user = userMapper.toEntity(request);

        return userRepository.create(user);
    }

    public GetUserResponse getById(long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        UserEntity user = optionalUser.orElseThrow(() -> new UserNotFoundByIdException(id));

        return userMapper.toResponse(user);
    }

    public GetUserResponse getByEmail(String email) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        UserEntity user = optionalUser.orElseThrow(() -> new UserNotFoundByEmailException(email));

        return userMapper.toResponse(user);
    }

    public UserEntity getFullUserByEmail(String email) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);

        return optionalUser.orElseThrow(() -> new UserNotFoundByEmailException(email));
    }

    public void deleteById(long id) {
        long affectedUsers = userRepository.deleteById(id);

        if(affectedUsers < 1) {
            throw new UserNotFoundByIdException(id);
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
            throw new UserNoContentsToUpdateException();
        }

        UserEntity userById = userRepository.findById(id).orElseThrow(() -> new UserNotFoundByIdException(id));

        if(request.name() != null) {
            userById.setName(request.name());
        }

        if(request.email() != null) {
            if(userRepository.findByEmail(request.email()).isPresent()) {
                throw new UserEmailAlreadyUsedException();
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
