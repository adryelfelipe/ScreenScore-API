package ctw.screenscoreapi.Movies.infra.repository.dao;

import ctw.screenscoreapi.Movies.domain.MovieEntity;
import ctw.screenscoreapi.Movies.domain.enums.Genre;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MovieDaoSpringJdbc {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public MovieDaoSpringJdbc(JdbcTemplate jdbcTemplate, SimpleJdbcInsert simpleJdbcInsert, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("Filmes")
                .usingGeneratedKeyColumns("id");
    }

    public long create(MovieEntity movie) {
        String sqlGenresInsert = "INSERT INTO Filme_Genero(id_filme, id_genero) VALUES(?, ?)";

        Map<String, Object> params = new HashMap<>();
        params.put("titulo", movie.getTitle());
        params.put("adulto", movie.isAdult());
        params.put("data_lancamento", Date.valueOf(movie.getReleaseDate()));
        params.put("lingua_original", movie.getOriginalLanguage());
        params.put("titulo_original", movie.getOriginalTitle());
        params.put("poster", movie.getPosterImage());
        params.put("visao_geral", movie.getOverview());
        long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        movie.setId(id);

        for(Genre genre: movie.getGenres()) {
            jdbcTemplate.update(
                    sqlGenresInsert,
                    movie.getId(),
                    genre.getId()
            );
        }

        return id;
    }
}
