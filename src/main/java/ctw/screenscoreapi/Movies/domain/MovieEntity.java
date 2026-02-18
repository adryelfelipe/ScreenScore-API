package ctw.screenscoreapi.Movies.domain;

public class MovieEntity {
    // Atributos
    private Long id;
    private String title;
    private String originalLanguage;
    private String originalTitle;
    private boolean adult;
    private String releaseDate;
    private String posterImage;

    // Construtor
    public MovieEntity(Long id, String posterImage, String releaseDate, boolean adult, String originalTitle, String originalLanguage, String title) {
        this.id = id;
        this.posterImage = posterImage;
        this.releaseDate = releaseDate;
        this.adult = adult;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }
}
