package ctw.screenscoreapi.Movies.domain.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum Genre {
    AVENTURA(1),
    FANTASIA(2),
    ANIMACAO(3),
    DRAMA(4),
    TERROR(5),
    ACAO(6),
    COMEDIA(7),
    HISTORIA(8),
    FAROESTE(9),
    THRILLER(10),
    CRIME(11),
    DOCUMENTARIO(12),
    FICCAO_CIENTIFICA(13),
    MISTERIO(14),
    MUSICA(15),
    ROMANCE(16),
    FAMILIA(17),
    GUERRA(18),
    CINEMA_TV(19);
    private static Map<Integer, Genre> BY_ID = Arrays.stream(values()).collect(Collectors.toMap(Genre::getId, genre -> genre));

    private final int id;

    Genre(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Optional<Genre> getGenreById(Integer id) {
        Genre genre = BY_ID.get(id);
        return Optional.ofNullable(genre);
    }
}
