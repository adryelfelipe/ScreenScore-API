package ctw.screenscoreapi.Movies.infra.repository.dao;

import ctw.screenscoreapi.Movies.domain.MovieEntity;
import ctw.screenscoreapi.Movies.domain.enums.Genre;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MovieDaoSpringJdbc {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public MovieDaoSpringJdbc(JdbcTemplate jdbcTemplate, DataSource dataSource) {
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

    public List<MovieEntity> findAllMovies() {
        String sqlMovies = "Select * From Filmes";

        return jdbcTemplate.query(
                sqlMovies,
                (rs,i) -> {
                    List<Genre> genres = findGenresByMovie(rs.getLong("id"));
                    MovieEntity movie = mapMovie(rs);
                    movie.setGenres(genres);
                    return movie;
                }
        );
    }

    public List<MovieEntity> findMovieByFilter(String title, List<Genre> genres) {

        StringBuilder sql = new StringBuilder("""
            SELECT DISTINCT F.*
            FROM Filmes F
            LEFT JOIN Filme_Genero FG ON F.id = FG.id_filme
            WHERE 1=1
        """);

        List<Object> params = new ArrayList<>();

        if (title != null) {
            sql.append(" AND LOWER(F.titulo) LIKE ?");
            params.add("%" + title.toLowerCase() + "%");
        }

        if (genres != null && !genres.isEmpty()) {
            String placeholders = genres.stream()
                    .map(g -> "?")
                    .collect(Collectors.joining(","));

            sql.append(" AND FG.id_genero IN (" + placeholders + ")");

            genres.forEach(g -> params.add(g.getId()));
        }

        List<MovieEntity> movies = jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                (rs, rowNum) -> mapMovie(rs)
        );

        movies.forEach(movie -> movie.setGenres(findGenresByMovie(movie.getId())));

        return movies;
    }

    private MovieEntity mapMovie(ResultSet rs) throws SQLException {
        return new MovieEntity(
                rs.getLong("id"),
                rs.getString("poster"),
                rs.getDate("data_lancamento").toString(),
                rs.getBoolean("adulto"),
                rs.getString("titulo_original"),
                rs.getString("lingua_original"),
                rs.getString("titulo"),
                rs.getString("visao_geral"),
                new ArrayList<>()
        );
    }

    private List<Genre> findGenresByMovie(long movieId) {

        String sql = "SELECT id_genero FROM Filme_Genero WHERE id_filme = ?";

        List<Integer> ids = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> rs.getInt("id_genero"),
                movieId
        );

        return ids.stream()
                .map(Genre::getGenreById)
                .toList();
    }

    public Optional<MovieEntity> findByExactTitle(String titulo) {
        String sql = "Select * from Filmes where titulo = ?";

        List<MovieEntity> movies = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    List<Genre> genres = findGenresByMovie(rs.getLong("id"));
                    MovieEntity movie = mapMovie(rs);
                    movie.setGenres(genres);

                    return movie;
                },
                titulo

        );

        return movies.stream().findFirst();
    }

    public int delete(long movieId) {
        String sql = "DELETE FROM Filmes WHERE id = ?";

        return jdbcTemplate.update(sql, movieId);
    }

    public void update(MovieEntity movie) {
        String sqlMovies =
                "UPDATE Filmes SET titulo = ?," +
                        " lingua_original = ?," +
                        " titulo_original = ?," +
                        " adulto = ?," +
                        " data_lancamento = ?," +
                        " poster = ?," +
                        " visao_geral = ? WHERE id = ?";

        String sqlGenre1 = "DELETE FROM Filme_Genero WHERE id_filme = ?";
        String sqlGenre2 = "INSERT INTO Filme_Genero(id_filme, id_genero) VALUES(?,?)";

        jdbcTemplate.update(
                sqlMovies,
                movie.getTitle(),
                movie.getOriginalLanguage(),
                movie.getOriginalTitle(),
                movie.isAdult(),
                movie.getReleaseDate(),
                movie.getPosterImage(),
                movie.getOverview(),
                movie.getId());

        jdbcTemplate.update(sqlGenre1, movie.getId());

        for(Genre genre : movie.getGenres()) {
            jdbcTemplate.update(sqlGenre2, movie.getId(), genre.getId());
        }
    }

    public Optional<MovieEntity> findById(long id) {
        String sql = "SELECT * FROM Filmes WHERE id = ?";

        List<MovieEntity> movies = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    MovieEntity movie = mapMovie(rs);
                    List<Genre> genres = findGenresByMovie(movie.getId());
                    movie.setGenres(genres);

                    return movie;
                },
                id
        );

        return movies.stream().findFirst();
    }
}
