package ctw.screenscoreapi.Module.Movies.application.exceptions;

import ctw.screenscoreapi.Share.exception.categories.DomainResourceNotFoundException;

public class MovieNotFoundByTitleException extends DomainResourceNotFoundException {
    public MovieNotFoundByTitleException() {
        super("Não foi possível identificar um filme com este título");
    }
}
