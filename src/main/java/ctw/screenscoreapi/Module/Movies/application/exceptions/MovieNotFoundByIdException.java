package ctw.screenscoreapi.Module.Movies.application.exceptions;

import ctw.screenscoreapi.Share.exception.categories.DomainResourceNotFoundException;

public class MovieNotFoundByIdException extends DomainResourceNotFoundException {
    public MovieNotFoundByIdException(long id) {
        super("Não foi possível encontrar um filme com o ID: " + id);
    }
}
