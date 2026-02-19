package ctw.screenscoreapi.Movies.application.exceptions;

public class MovieTitleAlreadyUsed extends RuntimeException {
    private String title;

    public MovieTitleAlreadyUsed(String title) {
        super("O título do filme já foi utilizado");
        this.title = title;
    }
}
