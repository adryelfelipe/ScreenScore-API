package ctw.screenscoreapi.Users.infra.repository.dao;

import ctw.screenscoreapi.Users.domain.entity.UserEntity;
import ctw.screenscoreapi.Users.domain.enums.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        params.put("tipo_usuario", user.getRole().name());

        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    public Optional<UserEntity> findById(long id) {
        String sql = "SELECT * FROM Usuarios WHERE id = ?";
        List<UserEntity> users = jdbcTemplate.query(sql, (rs, rowNumber) -> {

            return new UserEntity(
                    rs.getLong("id"),
                    rs.getString("senha"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    Role.valueOf(rs.getString("tipo_usuario"))
            );
        }, id);

        return users.stream().findFirst();
    }

    public Optional<UserEntity> findByEmail(String email) {
        String sql = "SELECT * FROM Usuarios WHERE email = ?";
        List<UserEntity> users = jdbcTemplate.query(sql, (rs, rowNumber) -> {

            return new UserEntity(
                    rs.getLong("id"),
                    rs.getString("senha"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    Role.valueOf(rs.getString("tipo_usuario"))
            );
        }, email);

        return users.stream().findFirst();
    }

    public long deleteById(long id) {
        String sql = "DELETE FROM Usuarios WHERE ID = ?";

        return jdbcTemplate.update(sql, id);
    }

    public List<UserEntity> getAll() {
        String sql = "SELECT * FROM Usuarios";
        List<UserEntity> users = jdbcTemplate.query(sql, (rs, num) -> {
            return new UserEntity(
                    rs.getLong("id"),
                    rs.getString("senha"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    Role.valueOf(rs.getString("tipo_usuario"))
            );
        });

        return users;
    }
}
