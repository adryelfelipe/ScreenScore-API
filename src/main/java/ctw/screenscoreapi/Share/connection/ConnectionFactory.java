package ctw.screenscoreapi.Share.connection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    // Atributos
    private static String url = System.getenv("SCREENSCORE_DATABASE_URL");
    private static String user = System.getenv("SCREENSCORE_DATABASE_USER");
    private static String password = System.getenv("SCREENSCORE_DATABASE_PASSWORD");

    // Metodos
    public static Connection getConnection() {
        try {

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("Erro ao se conectar com o banco de dados");

            return null;
        }
    }
}
