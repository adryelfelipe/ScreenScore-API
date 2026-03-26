package ctw.screenscoreapi.Avaliations.application.service;

import ctw.screenscoreapi.Avaliations.application.dtos.create.CreateAvaliationRequest;
import ctw.screenscoreapi.Avaliations.application.dtos.create.CreateAvaliationToEntity;
import ctw.screenscoreapi.Avaliations.application.mapper.AvaliationMapper;
import ctw.screenscoreapi.Avaliations.domain.AvaliationEntity;
import ctw.screenscoreapi.Avaliations.domain.AvaliationRepository;
import ctw.screenscoreapi.Movies.application.exceptions.MovieNotFoundByIdException;
import ctw.screenscoreapi.Movies.domain.MovieEntity;
import ctw.screenscoreapi.Movies.domain.repository.MovieRepository;
import ctw.screenscoreapi.Users.domain.repository.UserRepository;
import ctw.screenscoreapi.Users.infra.session.UserSession;
import org.springframework.stereotype.Service;

@Service
public class AvaliationService {
    private UserRepository userRepository;
    private MovieRepository movieRepository;
    private AvaliationMapper avaliationMapper;
    private UserSession userSession;
    private AvaliationRepository avaliationRepository;

    public AvaliationService(UserRepository userRepository, AvaliationMapper avaliationMapper, MovieRepository movieRepository, UserSession userSession, AvaliationRepository avaliationRepository) {
        this.userRepository = userRepository;
        this.avaliationMapper = avaliationMapper;
        this.movieRepository = movieRepository;
        this.userSession = userSession;
        this.avaliationRepository = avaliationRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public MovieRepository getMovieRepository() {
        return movieRepository;
    }

    public long create(CreateAvaliationRequest request) {
        long userId = userSession.getUserId();

        long movieId = request.movieId();
        MovieEntity movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundByIdException(movieId));

        CreateAvaliationToEntity createAvaliationToEntity = new CreateAvaliationToEntity(
                movieId,
                userId,
                request.score(),
                request.comment()
        );

        AvaliationEntity avaliation = avaliationMapper.toEntity(createAvaliationToEntity);

        return avaliationRepository.create(avaliation);
    }
}
