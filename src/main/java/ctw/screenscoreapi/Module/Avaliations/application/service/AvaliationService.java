package ctw.screenscoreapi.Module.Avaliations.application.service;

import ctw.screenscoreapi.Module.Auth.exception.UserNotAuthorizedException;
import ctw.screenscoreapi.Module.Avaliations.application.dtos.create.CreateAvaliationRequest;
import ctw.screenscoreapi.Module.Avaliations.application.dtos.create.CreateAvaliationToEntity;
import ctw.screenscoreapi.Module.Avaliations.application.dtos.get.GetAvaliationResponse;
import ctw.screenscoreapi.Module.Avaliations.application.dtos.get.GetListOfAvaliationResponse;
import ctw.screenscoreapi.Module.Avaliations.application.dtos.update.UpdateAvaliationRequest;
import ctw.screenscoreapi.Module.Avaliations.application.exception.AvaliationNoContentToUpdateException;
import ctw.screenscoreapi.Module.Avaliations.application.exception.AvaliationNotFoundByIdException;
import ctw.screenscoreapi.Module.Avaliations.application.mapper.AvaliationMapper;
import ctw.screenscoreapi.Module.Avaliations.domain.entity.AvaliationEntity;
import ctw.screenscoreapi.Module.Avaliations.domain.repository.AvaliationRepository;
import ctw.screenscoreapi.Module.Movies.application.exceptions.MovieNotFoundByIdException;
import ctw.screenscoreapi.Module.Movies.domain.entity.MovieEntity;
import ctw.screenscoreapi.Module.Movies.domain.repository.MovieRepository;
import ctw.screenscoreapi.Module.Users.domain.entity.UserEntity;
import ctw.screenscoreapi.Module.Users.domain.enums.Role;
import ctw.screenscoreapi.Module.Users.domain.repository.UserRepository;
import ctw.screenscoreapi.Module.Users.infra.session.UserSession;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void delete(long avaliationId) {
        Long userId = userSession.getUserId();

        AvaliationEntity avaliation = avaliationRepository.findById(avaliationId).orElseThrow(() -> new AvaliationNotFoundByIdException(avaliationId));

        UserEntity user = userRepository.findById(userId).orElseThrow();

        if(!avaliation.getUserId().equals(userId)) {
            if(user.getRole() != Role.ADMIN) {
                throw new UserNotAuthorizedException();
            }
        }

        avaliationRepository.deleteById(avaliationId);
    }

    public GetAvaliationResponse getById(Long id) {
        Optional<AvaliationEntity> optionalAvaliation = avaliationRepository.findById(id);
        AvaliationEntity avaliation = optionalAvaliation.orElseThrow(() -> new AvaliationNotFoundByIdException(id));

        return avaliationMapper.toResponse(avaliation);
    }

    public GetListOfAvaliationResponse getAll() {
        List<AvaliationEntity> avaliations = avaliationRepository.findAll();
        List<GetAvaliationResponse> response = avaliations.stream().map(avaliationMapper::toResponse).toList();

        return avaliationMapper.toResponse(response);
    }

    public void update(UpdateAvaliationRequest request, Long avaliationId) {
        if(request.comment() == null && request.score() == null) {
            throw new AvaliationNoContentToUpdateException();
        }

        Long userId = userSession.getUserId();

        Optional<AvaliationEntity> optionalAvaliation = avaliationRepository.findById(avaliationId);
        AvaliationEntity avaliation = optionalAvaliation.orElseThrow(() -> new AvaliationNotFoundByIdException(avaliationId));

        if(!avaliation.getUserId().equals(userId)) {
            throw new UserNotAuthorizedException();
        }

        if(request.comment() != null) {
            avaliation.setComment(request.comment());
        }

        if(request.score() != null) {
            avaliation.setScore(request.score());
        }

        avaliationRepository.update(avaliation);
    }
}
