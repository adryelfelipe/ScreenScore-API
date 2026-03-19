package ctw.screenscoreapi.Users.domain.repository;

import ctw.screenscoreapi.Users.domain.entity.UserEntity;

import java.util.Optional;

public interface UserRepository {
    long create(UserEntity user);
    Optional<UserEntity> getById(long id);
}
