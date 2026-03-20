package ctw.screenscoreapi.Auth.exception;

public class InvalidCredentialsApplicationException extends AuthApplicationException {
    public InvalidCredentialsApplicationException() {
        super("Email ou senha estão inválidos");
    }
}
