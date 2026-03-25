package ctw.screenscoreapi.Users.application.exception;

import ctw.screenscoreapi.Share.exception.categories.DataAlreadyUsedException;

public class UserEmailAlreadyUsedException extends DataAlreadyUsedException {
    public UserEmailAlreadyUsedException() {
        super("O email já foi utilizado");
    }
}
