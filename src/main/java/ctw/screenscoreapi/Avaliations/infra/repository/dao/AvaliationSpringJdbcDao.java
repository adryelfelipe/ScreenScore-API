package ctw.screenscoreapi.Avaliations.infra.repository.dao;

import ctw.screenscoreapi.Avaliations.domain.AvaliationEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

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
}
