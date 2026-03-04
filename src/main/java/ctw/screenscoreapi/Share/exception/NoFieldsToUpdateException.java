package ctw.screenscoreapi.Share.exception;

public class NoFieldsToUpdateException extends RuntimeException {
    public NoFieldsToUpdateException() {
        super("Nenhum campo foi enviado para atualização");
    }
}
