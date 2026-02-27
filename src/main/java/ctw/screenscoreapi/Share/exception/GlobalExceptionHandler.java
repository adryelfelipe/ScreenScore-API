package ctw.screenscoreapi.Share.exception;

import ctw.screenscoreapi.Movies.application.exceptions.MovieApplicationException;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${BASE_URL}" + "/errors")
    private String BASE_URL;
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) throws URISyntaxException {
        logger.warn("Erro ao processar requisicao, campos violados | path: {}", request.getRequestURI());

        URI type = new URI(BASE_URL + "/invalid-argument");
        URI instance = new URI(request.getRequestURI());
        String title = "A requisição contém campos inválidos";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Map<String, String> errors = new HashMap<>();

        for(FieldError fieldError : e.getFieldErrors()) {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }

        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setType(type);
        problemDetail.setInstance(instance);
        problemDetail.setTitle(title);
        problemDetail.setProperty("erros", errors);

        return ResponseEntity
                .status(status)
                .body(problemDetail);
    }

    @ExceptionHandler(MovieApplicationException.class)
    public ResponseEntity<ProblemDetail> handleMovieApplicationException(MovieApplicationException e, HttpServletRequest request) throws URISyntaxException {
        logger.warn("Erro ao processar requisicao, aplicacao violada | path: {}", request.getRequestURI());

        URI type = new URI(BASE_URL + "/application");
        URI instance = new URI(request.getRequestURI());
        String title = "Falha durante execução da aplicação";
        String detail = e.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setType(type);
        problemDetail.setInstance(instance);
        problemDetail.setDetail(detail);
        problemDetail.setTitle(title);

        return ResponseEntity
                .status(status)
                .body(problemDetail);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ProblemDetail> handleNoResourceFoundException(HttpServletRequest httpRequest) throws URISyntaxException {
        logger.warn("Erro ao processar requisicao, recurso nao encontrado | path: {}", httpRequest.getRequestURI());

        URI type = new URI(BASE_URL + "/no-resource-found");
        URI instace = new URI(httpRequest.getRequestURI());
        String title = "Recurso não encontrado";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setType(type);
        problemDetail.setTitle(title);
        problemDetail.setInstance(instace);

        return ResponseEntity
                .status(status)
                .body(problemDetail);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ProblemDetail> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException e, HttpServletRequest httpRequest) throws URISyntaxException {
        logger.warn("Erro ao processar requisicao, metodo http nao permitido | metodo: {} | path: {}", httpRequest.getMethod(), httpRequest.getRequestURI());

        URI type = new URI(BASE_URL + "/method-not-allowed");
        URI instance = new URI(httpRequest.getRequestURI());
        String title = "O método " + httpRequest.getMethod() + " não é permitido";
        String detail = "Ações permitidas: " + String.join(", ", e.getSupportedMethods());
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setType(type);
        problemDetail.setInstance(instance);
        problemDetail.setTitle(title);
        problemDetail.setDetail(detail);

        return ResponseEntity
                .status(status)
                .body(problemDetail);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ProblemDetail> handleFeignException(FeignException e, HttpServletRequest httpRequest) throws URISyntaxException {
        logger.error("Erro ao se comunicar com sistema externo: {} | {}", e.request().url(), e.getMessage());


        URI type = new URI(BASE_URL + "/external-server");
        URI instance = new URI(httpRequest.getRequestURI());
        String title = "Erro ao se comunicar com sistema externo";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setType(type);
        problemDetail.setInstance(instance);
        problemDetail.setTitle(title);

        return ResponseEntity
                .status(status)
                .body(problemDetail);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleMessageNotReadableException(HttpServletRequest httpRequest) throws URISyntaxException {
        logger.warn("Erro ao processar requisião, body inválido | path: {}", httpRequest.getRequestURI());

        URI type = new URI(BASE_URL + "/invalid-body");
        URI instace = new URI(httpRequest.getRequestURI());
        String title = "O body da requisição está ausente ou mal formatado";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setType(type);
        problemDetail.setInstance(instace);
        problemDetail.setTitle(title);

        return ResponseEntity
                .status(status)
                .body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleException(Exception e, HttpServletRequest httpRequest) throws URISyntaxException {
        logger.error("Erro interno do servidor: {}", e.getMessage());

        URI type = new URI(BASE_URL + "/generic-exception");
        URI instance = new URI(httpRequest.getRequestURI());
        String title = "Exceção genérica";
        String detail = "O servidor encontrou um erro inesperado";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setType(type);
        problemDetail.setInstance(instance);
        problemDetail.setTitle(title);
        problemDetail.setDetail(detail);

        return ResponseEntity
                .status(status)
                .body(problemDetail);
    }
}
