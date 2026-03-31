package ctw.screenscoreapi.Movies.domain.entity;

import ctw.screenscoreapi.Movies.domain.enums.Genre;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class MovieEntity {
    // Atributos
    private Long id;
    private String title;
    private String originalLanguage;
    private String originalTitle;
    private boolean adult;
    private String releaseDate;
    private String posterImage;
    private String overview;
    private List<Genre> genres;
    private List<Long> avaliationsIds;
    private BigDecimal averageScore;

    // Construtor
    public MovieEntity(Long id, String posterImage, String releaseDate, boolean adult, String originalTitle, String originalLanguage, String title, String overview, List<Genre> genres, List<Long> avaliationsIds, BigDecimal averageScore) {
        this.id = id;
        this.posterImage = posterImage;
        this.releaseDate = releaseDate;
        this.adult = adult;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.overview = overview;
        this.genres = genres;
        this.avaliationsIds = avaliationsIds;
        setAverageScore(averageScore);
    }

    // Getters e Setters
    public List<Long> getAvaliationsIds() {
        return avaliationsIds;
    }

    public void setAvaliationsIds(List<Long> avaliationsIds) {
        this.avaliationsIds = avaliationsIds;
    }

    public BigDecimal getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(BigDecimal averageScore) {
        if(averageScore == null) {
            this.averageScore = averageScore;
        } else {
            this.averageScore = averageScore.setScale(2, RoundingMode.HALF_UP);
        }
    }

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

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
