package ctw.screenscoreapi.Share.exception;

import ctw.screenscoreapi.Movies.application.exceptions.MovieApplicationException;
import ctw.screenscoreapi.Share.exception.categories.DataAlreadyUsedException;
import ctw.screenscoreapi.Share.exception.categories.DomainResourceNotFoundException;
import ctw.screenscoreapi.Share.exception.categories.NoContentToUpdateException;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static ProblemDetail problemDetailBuilder(URI type, URI instance, String title, HttpStatus status, String detail) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setType(type);
        problemDetail.setInstance(instance);
        problemDetail.setTitle(title);
        problemDetail.setDetail(detail);

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest httpRequest) throws URISyntaxException {
        logger.warn("400 (BAD_REQUEST) - Erro ao processar requisicao, campos violados | path: {}", httpRequest.getRequestURI());


        ProblemDetail problemDetail = problemDetailBuilder(
                URI.create("/erros/invalid-argument"),
                URI.create(httpRequest.getRequestURI()),
                "A requisição contém campos inválidos",
                HttpStatus.BAD_REQUEST,
                "Consulte a documentação do endpoint para visualizar os formatos esperados"
        );

        Map<String, String> errors = new HashMap<>();

        for(FieldError fieldError : e.getFieldErrors()) {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }

        problemDetail.setProperty("erros", errors);

        return ResponseEntity
                .status(problemDetail.getStatus())
                .body(problemDetail);
    }

    @ExceptionHandler(MovieApplicationException.class)
    public ResponseEntity<ProblemDetail> handleMovieApplicationException(MovieApplicationException e, HttpServletRequest request) throws URISyntaxException {
        logger.warn("400 (BAD_REQUEST) - Erro ao processar requisicao, aplicacao de filme violada | path: {}", request.getRequestURI());

        ProblemDetail problemDetail = problemDetailBuilder(
                URI.create("/erros/movie-application"),
                URI.create(request.getRequestURI()),
                "Falha durante execução da aplicação",
                HttpStatus.BAD_REQUEST,
                e.getMessage()
        );

        return ResponseEntity
                .status(problemDetail.getStatus())
                .body(problemDetail);
    }

    @ExceptionHandler(NoContentToUpdateException.class)
    public ResponseEntity<ProblemDetail> handleNoContentToUpdateException(NoContentToUpdateException e, HttpServletRequest request) throws URISyntaxException {
        logger.warn("422 (Unprocessable Entity) - Erro ao processar requisicao, nenhum conteúdo para atualizar | path: {}", request.getRequestURI());

        ProblemDetail problemDetail = problemDetailBuilder(
                URI.create("/erros/no-content-to-update"),
                URI.create(request.getRequestURI()),
                "Falha durante execução da aplicação",
                HttpStatus.UNPROCESSABLE_ENTITY,
                e.getMessage()
        );

        return ResponseEntity
                .status(problemDetail.getStatus())
                .body(problemDetail);
    }


    @ExceptionHandler(DomainResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleDomainResourceNotFoundException(DomainResourceNotFoundException e, HttpServletRequest request) throws URISyntaxException {
        logger.warn("404 (NOT_FOUND) - Erro ao processar requisicao, recurso nao encontrado | path: {}", request.getRequestURI());

        ProblemDetail problemDetail = problemDetailBuilder(
                URI.create("/erros/resource-not-found"),
                URI.create(request.getRequestURI()),
                "Recurso não encontrado",
                HttpStatus.NOT_FOUND,
                e.getMessage()
        );

        return ResponseEntity
                .status(problemDetail.getStatus())
                .body(problemDetail);
    }

    @ExceptionHandler(DataAlreadyUsedException.class)
    public ResponseEntity<ProblemDetail> handleDataAlreadyUsedException(DataAlreadyUsedException e, HttpServletRequest httpRequest) throws URISyntaxException {
        logger.warn("409 (CONFLICT) (FILME) - Erro ao processar requisicao, dados já registrados no banco | path: {}", httpRequest.getRequestURI());

        ProblemDetail problemDetail = problemDetailBuilder(
                URI.create("/erros/data-already-used"),
                URI.create(httpRequest.getRequestURI()),
                "Dados já registrados no servidor",
                HttpStatus.CONFLICT,
                e.getMessage()
        );

        return ResponseEntity
                .status(problemDetail.getStatus())
                .body(problemDetail);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ProblemDetail> handleNoResourceFoundException(HttpServletRequest httpRequest) throws URISyntaxException {
        logger.warn("404 (NOT_FOUND) - Erro ao processar requisicao, endpoint nao encontrado | path: {}", httpRequest.getRequestURI());

        ProblemDetail problemDetail = problemDetailBuilder(
                URI.create("/erros/endpoint-not-found"),
                URI.create(httpRequest.getRequestURI()),
                "Endpoint não encontrado",
                HttpStatus.NOT_FOUND,
                "Consulte a documentação da API para encontrar endpoints válidos"
        );

        return ResponseEntity
                .status(problemDetail.getStatus())
                .body(problemDetail);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ProblemDetail> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException e, HttpServletRequest httpRequest) throws URISyntaxException {
        logger.warn("405 (METHOD_NOT_ALLOWED) - Erro ao processar requisicao, metodo http nao permitido | metodo: {} | path: {}", httpRequest.getMethod(), httpRequest.getRequestURI());

        ProblemDetail problemDetail = problemDetailBuilder(
                URI.create("/erros/method-not-allowed"),
                URI.create(httpRequest.getRequestURI()),
                "O método " + httpRequest.getMethod() + " não é permitido",
                HttpStatus.METHOD_NOT_ALLOWED,
                "Ações permitidas: " + String.join(", ", e.getSupportedMethods())
        );

        return ResponseEntity
                .status(problemDetail.getStatus())
                .body(problemDetail);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ProblemDetail> handleFeignException(FeignException e, HttpServletRequest httpRequest) throws URISyntaxException {
        logger.error("502 (EXTERNAL_SERVER_ERROR) - Erro ao se comunicar com sistema externo: {} | {}", e.request().url(), e.getMessage());

        ProblemDetail problemDetail = problemDetailBuilder(
                URI.create("/erros/external-server"),
                URI.create(httpRequest.getRequestURI()),
                "Erro ao se comunicar com sistema externo",
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Falha ao comunicar com serviço externo. Tente novamente mais tarde."
        );

        return ResponseEntity
                .status(problemDetail.getStatus())
                .body(problemDetail);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest httpRequest) throws URISyntaxException {
        logger.warn("400 (BAD_REQUEST) - Erro ao processar requisião, body inválido | path: {}", httpRequest.getRequestURI());

        ProblemDetail problemDetail = problemDetailBuilder(
                URI.create("/erros/invalid-body"),
                URI.create(httpRequest.getRequestURI()),
                "O body da requisição está ausente ou mal formatado",
                HttpStatus.BAD_REQUEST,
                "Consulte a documentação do endpoint para visualizar o formato esperado"
        );

        return ResponseEntity
                .status(problemDetail.getStatus())
                .body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleException(Exception e, HttpServletRequest httpRequest) throws URISyntaxException {
        logger.error("500 (INTERNAL_SERVER_ERROR) - Erro interno do servidor: {}", e.getMessage());

        ProblemDetail problemDetail = problemDetailBuilder(
                URI.create("/erros/internal-server"),
                URI.create(httpRequest.getRequestURI()),
                "Erro interno do servidor",
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro interno no servidor. Tente novamente mais tarde."
        );

        return ResponseEntity
                .status(problemDetail.getStatus())
                .body(problemDetail);
    }
}
