package ctw.screenscoreapi.Movies.infra.repository.adapter;

import ctw.screenscoreapi.Movies.domain.MovieEntity;
import ctw.screenscoreapi.Movies.domain.enums.Genre;
import ctw.screenscoreapi.Movies.domain.repository.MovieRepository;
import ctw.screenscoreapi.Movies.infra.repository.dao.MovieDaoJdbc;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MovieRepositoryAdapter implements MovieRepository {
    // Atributos
    private MovieDaoJdbc movieDaoJdbc;

    // Construtor
    public MovieRepositoryAdapter(MovieDaoJdbc movieDaoJdbc) {
        this.movieDaoJdbc = movieDaoJdbc;
    }

    // Metodos
    @Override
    public long create(MovieEntity movie) {
        return movieDaoJdbc.create(movie);
    }

    @Override
    public List<MovieEntity> findMovieByFilter(String title, List<Genre> genres) {

        return movieDaoJdbc.findMovieByFilter(title, genres);
    }

    @Override
    public Optional<MovieEntity> findByExactTitle(String title) {

        return movieDaoJdbc.findByExactTitle(title);
    }

    @Override
    public long delete(long id) {
        return movieDaoJdbc.delete(id);
    }

    @Override
    public Optional<MovieEntity> findById(long id) {

        return movieDaoJdbc.findById(id);
    }

    @Override
    public List<MovieEntity> getAllMovies() {

        return movieDaoJdbc.findAllMovies();
    }

    @Override
    public void update(MovieEntity movie) {
        movieDaoJdbc.update(movie);
    }
}
