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
    public void create(MovieEntity movie) {
        movieDao.create(movie);
    }

    @Override
    public Optional<List<MovieEntity>> findByLikeTitle(String title) {

        return movieDao.findByLikeTitle(title);
    }

    @Override
    public Optional<MovieEntity> findByExactTitle(String title) {

        return movieDao.findByExactTitle(title);
    }
}
