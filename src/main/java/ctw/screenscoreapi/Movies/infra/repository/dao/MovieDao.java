package ctw.screenscoreapi.Movies.infra.repository.dao;

import ctw.screenscoreapi.Movies.domain.MovieEntity;
import ctw.screenscoreapi.Share.connection.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class MovieDao {
    // Atributos
    private Logger logger = LoggerFactory.getLogger(MovieDao.class);

    // Metodos
    public void create(MovieEntity movie) {
        String sqlInsert = "INSERT INTO Filmes (titulo, adulto, data_lancamento, lingua_original, titulo_original, poster) VALUES (?, ?, ?, ?, ?, ?)";

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement psInsert = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)){
            psInsert.setString(1, movie.getTitle());
            psInsert.setBoolean(2, movie.isAdult());
            psInsert.setDate(3, Date.valueOf(movie.getReleaseDate()));
            psInsert.setString(4, movie.getOriginalLanguage());
            psInsert.setString(5, movie.getOriginalTitle());
            psInsert.setString(6, movie.getPosterImage());
            psInsert.executeUpdate();

            ResultSet rs = psInsert.getGeneratedKeys();
            rs.next();
            movie.setId(rs.getLong(1));
        } catch(SQLException e) {
            logger.error("Erro ao inserir filme no banco de dados | ID: {}", movie.getId());
        }
    }
}
