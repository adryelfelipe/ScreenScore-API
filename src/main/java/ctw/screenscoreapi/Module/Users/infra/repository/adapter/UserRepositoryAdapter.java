package ctw.screenscoreapi.Module.Users.infra.repository.adapter;

import ctw.screenscoreapi.Module.Users.domain.entity.UserEntity;
import ctw.screenscoreapi.Module.Users.domain.repository.UserRepository;
import ctw.screenscoreapi.Module.Users.infra.repository.dao.UserDaoSpringJdbc;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public long deleteById(long id) {
        return userDaoSpringJdbc.deleteById(id);
    }

    @Override
    public List<UserEntity> getAll() {
        return userDaoSpringJdbc.getAll();
    }

    @Override
    public void update(UserEntity user) {
        userDaoSpringJdbc.update(user);
    }

    @Override
    public Optional<UserEntity> findById(long id) {
        return userDaoSpringJdbc.findById(id);
    }
}
