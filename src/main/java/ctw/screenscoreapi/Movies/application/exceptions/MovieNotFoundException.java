package ctw.screenscoreapi.Movies.application.exceptions;

import ctw.screenscoreapi.Share.exception.categories.DomainResourceNotFoundException;

public class MovieNotFoundException extends DomainResourceNotFoundException {
    public MovieNotFoundException(String message) {
        super(message);
    }
}
