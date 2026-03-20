package ctw.screenscoreapi.Users.infra.repository.adapter;

import ctw.screenscoreapi.Users.domain.entity.UserEntity;
import ctw.screenscoreapi.Users.domain.repository.UserRepository;
import ctw.screenscoreapi.Users.infra.repository.dao.UserDaoSpringJdbc;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryAdapter implements UserRepository {
    private UserDaoSpringJdbc userDaoSpringJdbc;

    public UserRepositoryAdapter(UserDaoSpringJdbc userDaoSpringJdbc) {
        this.userDaoSpringJdbc = userDaoSpringJdbc;
    }

    @Override
    public long create(UserEntity user) {
        return userDaoSpringJdbc.create(user);
    }


    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userDaoSpringJdbc.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> findById(long id) {
        return userDaoSpringJdbc.findById(id);
    }
}
