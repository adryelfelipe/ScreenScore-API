package ctw.screenscoreapi.Auth.exception;

public class UserNotAuthorizedException extends RuntimeException {
    public UserNotAuthorizedException() {
        super("Você não possui permissão para tal operação");
    }
}
