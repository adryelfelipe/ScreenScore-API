package ctw.screenscoreapi.Users.application.exception;

import ctw.screenscoreapi.Share.exception.categories.DomainResourceNotFoundException;

public class UserNotFoundByEmailException extends DomainResourceNotFoundException {
    public UserNotFoundByEmailException(String email) {
        super(email);
    }
}
