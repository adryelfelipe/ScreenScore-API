package ctw.screenscoreapi.Movies.infra.repository.dao;

import ctw.screenscoreapi.Movies.application.exceptions.MovieUnkwonGenreException;
import ctw.screenscoreapi.Movies.domain.MovieEntity;
import ctw.screenscoreapi.Movies.domain.enums.Genre;
import ctw.screenscoreapi.Movies.infra.repository.exception.DatabaseException;
import ctw.screenscoreapi.Share.connection.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MovieDao {
    // Atributos
    private Logger logger = LoggerFactory.getLogger(MovieDao.class);

    // Metodos
    public void create(MovieEntity movie) {
        String sqlMoviesInsert = "INSERT INTO Filmes (titulo, adulto, data_lancamento, lingua_original, titulo_original, poster, visao_geral) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlGenresInsert = "INSERT INTO Filme_Genero(id_filme, id_genero) VALUES(?, ?)";

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement psInsert = connection.prepareStatement(sqlMoviesInsert, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement psGeneres = connection.prepareStatement(sqlGenresInsert)){
            psInsert.setString(1, movie.getTitle());
            psInsert.setBoolean(2, movie.isAdult());
            psInsert.setDate(3, Date.valueOf(movie.getReleaseDate()));
            psInsert.setString(4, movie.getOriginalLanguage());
            psInsert.setString(5, movie.getOriginalTitle());
            psInsert.setString(6, movie.getPosterImage());
            psInsert.setString(7, movie.getOverview());
            psInsert.executeUpdate();

            ResultSet rs = psInsert.getGeneratedKeys();
            rs.next();
            long movieId = rs.getLong(1);
            movie.setId(movieId);


            for(Genre genre: movie.getGenres()) {
                psGeneres.setLong(1, movieId);
                psGeneres.setLong(2, genre.getId());
                psGeneres.executeUpdate();
            }

        } catch(SQLException e) {
            logger.error("Erro ao inserir filme no banco de dados | {}", e.getMessage());

            throw new DatabaseException(e.getMessage());
        }
    }

    public Optional<List<MovieEntity>> findByLikeTitle(String title) {
        String sqlMovies = "SELECT * FROM Filmes WHERE LOWER(titulo) LIKE ?";
        String sqlGenres = "SELECT id_genero FROM Filme_Genero WHERE id_filme = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement psMovies = connection.prepareStatement(sqlMovies);
             PreparedStatement psGenre = connection.prepareStatement(sqlGenres)) {
            psMovies.setString(1, "%" + title.toLowerCase() + "%");
            ResultSet rsMovie = psMovies.executeQuery();

            if (!rsMovie.isBeforeFirst()) {
                return Optional.empty();
            }

            List<MovieEntity> movies = new ArrayList<>();

            while (rsMovie.next()) {
                long movieId = rsMovie.getLong("id");
                psGenre.setLong(1, movieId);
                ResultSet rsGenre = psGenre.executeQuery();
                List<Integer> genreIds = new ArrayList<>();

                while (rsGenre.next()) {
                    int genreId = rsGenre.getInt("id_genero");
                    genreIds.add(genreId);
                }

                List<Genre> genres = genreIds.stream()
                        .map(genreId -> Genre.getGenreById(genreId)
                                .orElseThrow(() -> new MovieUnkwonGenreException(genreId)))
                        .toList();

                MovieEntity movie = new MovieEntity(
                        movieId,
                        rsMovie.getString("poster"),
                        rsMovie.getDate("data_lancamento").toString(),
                        rsMovie.getBoolean("adulto"),
                        rsMovie.getString("titulo_original"),
                        rsMovie.getString("lingua_original"),
                        rsMovie.getString("titulo"),
                        rsMovie.getString("visao_geral"),
                        genres
                );

                movies.add(movie);
            }

            return Optional.of(movies);
        } catch (SQLException e) {
            logger.error("Erro ao buscar filme com título | {}", e.getMessage());


            throw new DatabaseException(e.getMessage());
        }
    }

    public Optional<MovieEntity> findByExactTitle(String title) {
        String sqlMovies = "SELECT * FROM Filmes WHERE titulo = ?";
        String sqlGenres = "SELECT id_genero FROM Filme_Genero WHERE id_filme = ?";

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement psMovies = connection.prepareStatement(sqlMovies);
            PreparedStatement psGenre = connection.prepareStatement(sqlGenres)) {
            psMovies.setString(1, title);
            ResultSet rsMovie = psMovies.executeQuery();

            if(!rsMovie.next()) {
                return Optional.empty();
            }

            long movieId = rsMovie.getLong("id");
            psGenre.setLong(1, movieId);
            ResultSet rsGenre = psGenre.executeQuery();

            List<Integer> genreIds = new ArrayList<>();

            while(rsGenre.next()) {
                int genreId = rsGenre.getInt("id_genero");
                genreIds.add(genreId);
            }

            List<Genre> genres = genreIds.stream()
                                         .map(genreId -> Genre.getGenreById(genreId)
                                                 .orElseThrow(() -> new MovieUnkwonGenreException(genreId)))
                                         .toList();

            MovieEntity movie = new MovieEntity(
                    movieId,
                    rsMovie.getString("poster"),
                    rsMovie.getDate("data_lancamento").toString(),
                    rsMovie.getBoolean("adulto"),
                    rsMovie.getString("titulo_original"),
                    rsMovie.getString("lingua_original"),
                    rsMovie.getString("titulo"),
                    rsMovie.getString("visao_geral"),
                    genres
            );

            return Optional.of(movie);
        } catch(SQLException e) {
            logger.error("Erro ao buscar filme com título | {}", e.getMessage());


            throw new DatabaseException(e.getMessage());
        }
    }

    public long delete(long id) {
        String sql = "DELETE FROM Filmes WHERE ID = ?";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Erro ao inserir filme no banco de dados | {}", e.getMessage());

            throw new DatabaseException(e.getMessage());
        }
    }
}
