package ctw.screenscoreapi.Movies.infra.repository.exception;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }
}
