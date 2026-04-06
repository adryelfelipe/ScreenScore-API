package ctw.screenscoreapi.Module.Movies.infra.repository.exception;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }
}
