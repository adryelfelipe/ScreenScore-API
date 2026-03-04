package ctw.screenscoreapi.Movies.application.exceptions;

import ctw.screenscoreapi.Share.exception.categories.DataAlreadyUsedException;

public class MovieTitleAlreadyUsedException extends DataAlreadyUsedException {
    public MovieTitleAlreadyUsedException(String title) {
        super("O título do filme já foi utilizado");
    }
}
