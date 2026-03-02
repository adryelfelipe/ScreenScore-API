package ctw.screenscoreapi.Movies.application.exceptions;

public class MovieTitleAlreadyUsedException extends MovieDataAlreadyUsedException {
    public MovieTitleAlreadyUsedException(String title) {
        super("O título do filme já foi utilizado");
    }
}
