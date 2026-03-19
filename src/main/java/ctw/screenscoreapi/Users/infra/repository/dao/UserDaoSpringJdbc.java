package ctw.screenscoreapi.Users.infra.repository.dao;

import ctw.screenscoreapi.Users.domain.entity.UserEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserDaoSpringJdbc {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public UserDaoSpringJdbc(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("Usuarios")
                .usingGeneratedKeyColumns("id");
    }

    public long create(UserEntity user) {
        Map<String, Object> params = new HashMap<>();
        params.put("nome", user.getName());
        params.put("email", user.getEmail());
        params.put("senha", user.getPassword());
        params.put("tipo_usuario", user.getRole());

        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }
}
