package ctw.screenscoreapi.Users.application.exception;

import ctw.screenscoreapi.Share.exception.categories.DomainResourceNotFoundException;

public class UserNotFoundByIdException extends DomainResourceNotFoundException {
    public UserNotFoundByIdException(long id) {
        super("Usuário não encontrado com o id: " + id);
    }
}
