package ctw.screenscoreapi.Users.domain.repository;

import ctw.screenscoreapi.Users.domain.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    long create(UserEntity user);
    Optional<UserEntity> findById(long id);
    Optional<UserEntity> findByEmail(String email);
    long deleteById(long id);
    List<UserEntity> getAll();
}
