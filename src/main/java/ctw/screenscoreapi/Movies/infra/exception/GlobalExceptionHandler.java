package ctw.screenscoreapi.Movies.infra.exception;

import ctw.screenscoreapi.Movies.application.exceptions.MovieApplicationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Value("${BASE_URL}")
    private String BASE_URL;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) throws URISyntaxException {
        URI type = new URI(BASE_URL + "/errors/invalid-argument");
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
        URI type = new URI(BASE_URL + "/errors/business-rule");
        URI instance = new URI(request.getRequestURI());
        String title = "Violação de regra de negócio";
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
}
