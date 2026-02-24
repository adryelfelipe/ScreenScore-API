package ctw.screenscoreapi.Movies.application.controller;

import ctw.screenscoreapi.Movies.application.dtos.create.CreateMovieRequest;
import ctw.screenscoreapi.Movies.application.dtos.get.GetExternalMovieRequest;
import ctw.screenscoreapi.Movies.application.dtos.get.GetExternalMovieResponse;
import ctw.screenscoreapi.Movies.application.dtos.get.GetMovieResponse;
import ctw.screenscoreapi.Movies.application.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/filmes")
@Tag(name = "Filmes", description = "Endpoints para gerenciamento de filmes")
public class MovieController {
    // Atributos
    private MovieService movieService;

    // Construtor
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // Endpoints
    @PostMapping
    @Operation(
            summary = "Cadastra um novo filme",
            description = "Cadastra um novo filme no sistema caso não exista nenhum outro registrado com o mesmo título"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Filme criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Os dados fornecidos estão inválidos")
    })
    public ResponseEntity<Void> create(@Valid @RequestBody CreateMovieRequest request) {
       movieService.create(request);

       return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/externo")
    public ResponseEntity<GetExternalMovieResponse> getExternalMovie(@Valid GetExternalMovieRequest request) {
        GetExternalMovieResponse response = movieService.getExternal(request);

        return ResponseEntity
                .ok()
                .body(response);
    }
}
