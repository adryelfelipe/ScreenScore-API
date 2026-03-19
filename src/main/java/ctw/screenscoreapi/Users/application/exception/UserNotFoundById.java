package ctw.screenscoreapi.Users.application.exception;

import ctw.screenscoreapi.Share.exception.categories.DomainResourceNotFoundException;

public class UserNotFoundById extends DomainResourceNotFoundException {
    public UserNotFoundById(long id) {
        super("Usuário não encontrado com o id: " + id);
    }
}
