package ctw.screenscoreapi.Module.Avaliations.domain.entity;

import java.math.BigDecimal;

public class AvaliationEntity {
    private Long id;
    private Long userId;
    private Long movieId;
    private BigDecimal score;
    private String comment;

    public AvaliationEntity(Long id, String comment, BigDecimal score, Long movieId, Long userId) {
        this.id = id;
        this.comment = comment;
        this.score = score;
        this.movieId = movieId;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
