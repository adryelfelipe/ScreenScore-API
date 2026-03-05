package ctw.screenscoreapi.Movies.infra.repository.adapter;

import ctw.screenscoreapi.Movies.domain.MovieEntity;
import ctw.screenscoreapi.Movies.domain.repository.MovieRepository;
import ctw.screenscoreapi.Movies.infra.repository.dao.MovieDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MovieRepositoryAdapter implements MovieRepository {
    // Atributos
    private MovieDao movieDao;

    // Construtor
    public MovieRepositoryAdapter(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    // Metodos
    @Override
    public long create(MovieEntity movie) {
        return movieDao.create(movie);
    }

    @Override
    public List<MovieEntity> findByLikeTitle(String title) {

        return movieDao.findByLikeTitle(title);
    }

    @Override
    public Optional<MovieEntity> findByExactTitle(String title) {

        return movieDao.findByExactTitle(title);
    }

    @Override
    public long delete(long id) {
        return movieDao.delete(id);
    }

    @Override
    public Optional<MovieEntity> findById(long id) {

        return movieDao.findById(id);
    }

    @Override
    public List<MovieEntity> getAllMovies() {

        return movieDao.findAllMovies();
    }

    @Override
    public void update(MovieEntity movie) {
        movieDao.update(movie);
    }
}
