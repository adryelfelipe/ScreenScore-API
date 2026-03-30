package ctw.screenscoreapi.Auth.exception;

public class UserNotAuthenticatedException extends AuthException {
    public UserNotAuthenticatedException() {
        super("Erro, usuário não autenticado");
    }
}
