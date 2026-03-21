package ctw.screenscoreapi.Users.application.exception;

import ctw.screenscoreapi.Share.exception.categories.DomainResourceNotFoundException;

public class UserNotFoundByEmail extends DomainResourceNotFoundException {
    public UserNotFoundByEmail(String email) {
        super(email);
    }
}
