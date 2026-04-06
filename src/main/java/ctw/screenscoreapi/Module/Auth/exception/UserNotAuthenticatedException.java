package ctw.screenscoreapi.Module.Auth.exception;

public class UserNotAuthenticatedException extends AuthException {
    public UserNotAuthenticatedException() {
        super("Erro, usuário não autenticado");
    }
}
