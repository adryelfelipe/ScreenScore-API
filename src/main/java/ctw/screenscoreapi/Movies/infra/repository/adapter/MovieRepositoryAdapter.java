package ctw.screenscoreapi.Movies.infra.repository.adapter;

import ctw.screenscoreapi.Movies.domain.entity.MovieEntity;
import ctw.screenscoreapi.Movies.domain.enums.Genre;
import ctw.screenscoreapi.Movies.domain.repository.MovieRepository;
import ctw.screenscoreapi.Movies.infra.repository.dao.MovieDaoJdbc;
import ctw.screenscoreapi.Movies.infra.repository.dao.MovieDaoSpringJdbc;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MovieRepositoryAdapter implements MovieRepository {
    // Atributos
    private MovieDaoJdbc movieDaoJdbc;
    private MovieDaoSpringJdbc movieDaoSpringJdbc;

    // Construtor
    public MovieRepositoryAdapter(MovieDaoJdbc movieDaoJdbc, MovieDaoSpringJdbc movieDaoSpringJdbc) {
        this.movieDaoJdbc = movieDaoJdbc;
        this.movieDaoSpringJdbc = movieDaoSpringJdbc;
    }

    // Metodos
    @Override
    public long create(MovieEntity movie) {
        return movieDaoSpringJdbc.create(movie);
    }

    @Override
    public List<MovieEntity> findMovieByFilter(String title, List<Genre> genres) {

        return movieDaoSpringJdbc.findMovieByFilter(title, genres);
    }

    @Override
    public Optional<MovieEntity> findByExactTitle(String title) {

        return movieDaoSpringJdbc.findByExactTitle(title);
    }

    @Override
    public int delete(long id) {
        return movieDaoSpringJdbc.delete(id);
    }

    @Override
    public Optional<MovieEntity> findById(long id) {

        return movieDaoSpringJdbc.findById(id);
    }

    @Override
    public List<MovieEntity> findAllMovies() {

        return movieDaoSpringJdbc.findAllMovies();
    }

    @Override
    public void update(MovieEntity movie) {
        movieDaoSpringJdbc.update(movie);
    }

    @Override
    public List<MovieEntity> findTop10Movies() {
        return movieDaoSpringJdbc.findTop10Movies();
    }
}
