package ctw.screenscoreapi.Users.application.exception;

import ctw.screenscoreapi.Share.exception.categories.DataAlreadyUsedException;

public class UserEmailAlreadyUsed extends DataAlreadyUsedException {
    public UserEmailAlreadyUsed() {
        super("O email já foi utilizado");
    }
}
