package ctw.screenscoreapi.Users.domain.repository;

import ctw.screenscoreapi.Users.domain.entity.UserEntity;

public interface UserRepository {
    long create(UserEntity user);
}
