package ctw.screenscoreapi.Share.exception.categories;

public class NoContentToUpdateException extends RuntimeException {
    public NoContentToUpdateException() {
        super("Nenhum campo foi enviado para atualização");
    }
}
