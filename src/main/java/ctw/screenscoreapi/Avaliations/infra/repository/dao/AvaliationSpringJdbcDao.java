package ctw.screenscoreapi.Avaliations.infra.repository.dao;

import ctw.screenscoreapi.Avaliations.domain.AvaliationEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AvaliationSpringJdbcDao {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public AvaliationSpringJdbcDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("Avaliacoes")
                .usingGeneratedKeyColumns("id");
    }

    public long create(AvaliationEntity avaliation) {
        Map<String, Object> params  = new HashMap<>();
        params.put("id_usuario", avaliation.getUserId());
        params.put("id_filme", avaliation.getMovieId());
        params.put("nota", avaliation.getScore());
        params.put("comentario", avaliation.getComment());

        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    public long deleteById(long id) {
        String sql = "DELETE FROM Avaliacoes WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }

    public Optional<AvaliationEntity> findById(long id) {
        String sql = "SELECT * FROM Avaliacoes WHERE id = ?";

        List<AvaliationEntity> avaliations = jdbcTemplate.query(sql, (rs, num) -> {

            return new AvaliationEntity(
                    rs.getLong("id"),
                    rs.getString("comentario"),
                    rs.getBigDecimal("nota"),
                    rs.getLong("id_filme"),
                    rs.getLong("id_usuario")
            );
        }, id);

        return avaliations.stream().findFirst();
    }
}
