package ctw.screenscoreapi.Avaliations.application.exception;

import ctw.screenscoreapi.Share.exception.categories.DomainResourceNotFoundException;

public class AvaliationNotFoundByIdException extends DomainResourceNotFoundException {
    public AvaliationNotFoundByIdException(long id) {
        super("Avaliação não encontrada com o id: " + id);
    }
}
