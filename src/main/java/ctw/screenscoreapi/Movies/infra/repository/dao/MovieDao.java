package ctw.screenscoreapi.Movies.infra.repository.dao;

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
import java.util.stream.Collectors;

@Repository
public class MovieDao {
    // Atributos
    private Logger logger = LoggerFactory.getLogger(MovieDao.class);

    // Metodos
    public long create(MovieEntity movie) {
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

            return movieId;

        } catch(SQLException e) {
            logger.error("Erro ao inserir filme no banco de dados | {}", e.getMessage());

            throw new DatabaseException(e.getMessage());
        }
    }

    public List<MovieEntity> findMovieByFilter(String title, List<Genre> genres) {
        if(title != null && genres == null){
            String sqlMovies = "SELECT * FROM Filmes WHERE LOWER(titulo) LIKE ?";
            String sqlGenres = "SELECT distinct id_genero FROM Filme_Genero WHERE id_filme = ?";

            try (Connection connection = ConnectionFactory.getConnection();
                 PreparedStatement psMovies = connection.prepareStatement(sqlMovies);
                 PreparedStatement psGenre = connection.prepareStatement(sqlGenres)) {
                psMovies.setString(1, "%" + title.toLowerCase() + "%");
                ResultSet rsMovie = psMovies.executeQuery();

                List<MovieEntity> movies = new ArrayList<>();

                while (rsMovie.next()) {
                    List<Integer> genreIds = new ArrayList<>();

                    long movieId = rsMovie.getLong("id");
                    psGenre.setLong(1, movieId);
                    ResultSet rsGenre = psGenre.executeQuery();

                    while (rsGenre.next()) {
                        int genreId = rsGenre.getInt("id_genero");
                        genreIds.add(genreId);
                    }

                    List<Genre> genresl = genreIds.stream()
                            .map(Genre::getGenreById)
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
                            genresl
                    );

                    movies.add(movie);
                }

                return movies;
            } catch (SQLException e) {
                logger.error("Erro ao buscar filme com título | {}", e.getMessage());

                throw new DatabaseException(e.getMessage());
            }
        }

        if(title == null && genres != null) {
            String genresList = genres
                    .stream()
                    .map(Genre::getId)
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));

            String sql =
                    """
                    Select distinct
                        F.* 
                    From 
                        Filmes F
                    Join Filme_Genero FG ON F.id = FG.id_filme
                    Where FG.id_genero IN (""" + genresList + ")";

            String sqlGenre = "SELECT distinct id_genero FROM Filme_Genero WHERE id_filme = ?";

            try (Connection connection = ConnectionFactory.getConnection();
                 PreparedStatement psMovies = connection.prepareStatement(sql);
                 PreparedStatement psGenre = connection.prepareStatement(sqlGenre)){
                ResultSet rsMovie = psMovies.executeQuery();

                List<MovieEntity> movies = new ArrayList<>();

                while (rsMovie.next()) {
                    List<Integer> genreIds = new ArrayList<>();

                    long movieId = rsMovie.getLong("id");
                    psGenre.setLong(1, movieId);
                    ResultSet rsGenre = psGenre.executeQuery();

                    while(rsGenre.next()) {
                        genreIds.add(rsGenre.getInt("id_genero"));
                    }

                    List<Genre> genresl = genreIds
                            .stream()
                            .map(Genre::getGenreById)
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
                            genresl
                    );

                    movies.add(movie);
                }

                    return movies;
                } catch(SQLException e) {
                    logger.error("Erro ao buscar filme com gêneros | {}", e.getMessage());

                    throw new DatabaseException(e.getMessage());
                }
            }

        if(title != null && genres != null) {
            String genresList = genres
                    .stream()
                    .map(genre -> String.valueOf(genre.getId()))
                    .collect(Collectors.joining(","));

            String sql =
                    """
                    Select distinct
                        F.* 
                    From 
                        Filmes F 
                    Join Filme_Genero FG ON F.id = FG.id_filme
                    Where Lower(F.titulo) LIKE ? and FG.id_genero IN (""" + genresList + ")";


            String sqlGenre = "SELECT id_genero FROM Filme_Genero WHERE id_filme = ?";
            try (Connection connection = ConnectionFactory.getConnection();
                 PreparedStatement psMovies = connection.prepareStatement(sql);
                 PreparedStatement psGenre = connection.prepareStatement(sqlGenre)) {

                psMovies.setString(1, "%" + title.toLowerCase() + "%");
                ResultSet rsMovie = psMovies.executeQuery();

                List<MovieEntity> movies = new ArrayList<>();

                while (rsMovie.next()) {
                    List<Integer> genreIds = new ArrayList<>();

                    long movieId = rsMovie.getLong("id");
                    psGenre.setLong(1, movieId);
                    ResultSet rsGenre = psGenre.executeQuery();

                    while(rsGenre.next()) {
                        genreIds.add(rsGenre.getInt("id_genero"));
                    }

                    List<Genre> genresl = genreIds
                            .stream()
                            .map(Genre::getGenreById)
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
                            genresl
                    );

                    movies.add(movie);
                }

                return movies;
            } catch (SQLException e) {
                logger.error("Erro ao buscar filme com título e gênero | {}", e.getMessage());

                throw new DatabaseException(e.getMessage());
            }
        }

        return null;
    }

    public List<MovieEntity> findAllMovies() {
        String sqlMovies = "SELECT * FROM Filmes";
        String sqlGenres = "SELECT * FROM Filme_Genero WHERE id_filme = ?";

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement psMovies = connection.prepareStatement(sqlMovies);
            PreparedStatement psGenre = connection.prepareStatement(sqlGenres))  {

            ResultSet rsMovies = psMovies.executeQuery();

            List<MovieEntity> movies = new ArrayList<>();

            while(rsMovies.next()) {
                List<Integer> genreIds = new ArrayList<>();

                long movieId = rsMovies.getLong("id");

                psGenre.setLong(1, movieId);
                ResultSet rsGenres = psGenre.executeQuery();

                while(rsGenres.next()) {
                    int genreId = rsGenres.getInt("id_genero");
                    genreIds.add(genreId);
                }

                List<Genre> genres = genreIds.stream().map(Genre::getGenreById).toList();

                MovieEntity movie = new MovieEntity(
                        movieId,
                        rsMovies.getString("poster"),
                        rsMovies.getDate("data_lancamento").toString(),
                        rsMovies.getBoolean("adulto"),
                        rsMovies.getString("titulo_original"),
                        rsMovies.getString("lingua_original"),
                        rsMovies.getString("titulo"),
                        rsMovies.getString("visao_geral"),
                        genres
                );

                movies.add(movie);
            }

            return movies;

        } catch (SQLException e) {
            logger.error("Erro ao encontrar filmes no banco de dados | {}", e.getMessage());

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

            if(!rsMovie.isBeforeFirst()) {
                return Optional.empty();
            }

            rsMovie.next();

            long movieId = rsMovie.getLong("id");
            psGenre.setLong(1, movieId);
            ResultSet rsGenre = psGenre.executeQuery();

            List<Integer> genreIds = new ArrayList<>();

            while(rsGenre.next()) {
                int genreId = rsGenre.getInt("id_genero");
                genreIds.add(genreId);
            }

            List<Genre> genres = genreIds.stream()
                                         .map(Genre::getGenreById)
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
            logger.error("Erro ao deletar filme no banco de dados | {}", e.getMessage());

            throw new DatabaseException(e.getMessage());
        }
    }

    public Optional<MovieEntity> findById(long id) {
        String sqlMovie = "SELECT * FROM Filmes WHERE id = ?";
        String sqlGenres = "SELECT * FROM Filme_Genero WHERE id_filme = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement psMovie = conn.prepareStatement(sqlMovie);
            PreparedStatement psGenres = conn.prepareStatement(sqlGenres)) {

            psMovie.setLong(1, id);
            ResultSet rsMovie = psMovie.executeQuery();

            if(!rsMovie.isBeforeFirst()) {
                return Optional.empty();
            }

            rsMovie.next();

            psGenres.setLong(1, id);
            ResultSet rsGenres = psGenres.executeQuery();
            List<Integer> genresIdList = new ArrayList<>();

            while(rsGenres.next()) {
                int idGenre = rsGenres.getInt("id_genero");
                genresIdList.add(idGenre);
            }

            List<Genre> genresList = genresIdList.stream().map(Genre::getGenreById).toList();

            MovieEntity movie = new MovieEntity(
                    rsMovie.getLong("id"),
                    rsMovie.getString("poster"),
                    rsMovie.getDate("data_lancamento").toString(),
                    rsMovie.getBoolean("adulto"),
                    rsMovie.getString("titulo_original"),
                    rsMovie.getString("lingua_original"),
                    rsMovie.getString("titulo"),
                    rsMovie.getString("visao_geral"),
                    genresList
            );

            return Optional.of(movie);
        } catch (SQLException e) {
            logger.error("Erro ao encontrar filme no banco de dados | {}", e.getMessage());

            throw new DatabaseException(e.getMessage());
        }
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

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement psMovie = conn.prepareStatement(sqlMovies);
            PreparedStatement psGenre1 = conn.prepareStatement(sqlGenre1);
            PreparedStatement psGenre2 = conn.prepareStatement(sqlGenre2)) {

            psMovie.setString(1, movie.getTitle());
            psMovie.setString(2, movie.getOriginalLanguage());
            psMovie.setString(3, movie.getOriginalTitle());
            psMovie.setBoolean(4, movie.isAdult());
            psMovie.setString(5, movie.getReleaseDate());
            psMovie.setString(6, movie.getPosterImage());
            psMovie.setString(7, movie.getOverview());
            psMovie.setLong(8, movie.getId());
            psMovie.executeUpdate();

            psGenre1.setLong(1, movie.getId());
            psGenre1.executeUpdate();

            for(Genre genre : movie.getGenres()) {
                psGenre2.setLong(1, movie.getId());
                psGenre2.setLong(2, genre.getId());
                psGenre2.executeUpdate();
            }

        } catch (SQLException e) {
            logger.error("Erro ao atualizar filme no banco de dados | {}", e.getMessage());

            throw new DatabaseException(e.getMessage());
        }
    }
}
