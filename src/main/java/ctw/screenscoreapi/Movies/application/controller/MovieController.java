package ctw.screenscoreapi.Movies.application.controller;

import ctw.screenscoreapi.Movies.application.dtos.create.CreateMovieRequest;
import ctw.screenscoreapi.Movies.application.dtos.get.GetMoviesByTitleResponse;
import ctw.screenscoreapi.Movies.application.dtos.get.GetMovieResponse;
import ctw.screenscoreapi.Movies.application.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
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
            summary = "Cadastra um novo filme.",
            description = "Cadastra um novo filme no sistema caso não exista nenhum outro registrado com o mesmo título."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Filme criado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Os dados fornecidos estão inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class, example =
                            """
                           {
                               "instance": "/filmes",
                                "status": 400,
                                "title": "A requisição contém campos inválidos",
                                "type": "http://localhost:8080/errors/invalid-argument",
                                "erros": {
                                    "overview": "A visão geral do filme deve possuir no mínimo 5 caracteres"
                                }
                           }        
                            """))
            )
    })
    public ResponseEntity<Void> create(@Valid @RequestBody CreateMovieRequest request) {
       movieService.create(request);

       return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/externo")
    @Operation (
            summary = "Retorna filmes de uma api externa.",
            description = "Retorna uma lista de filmes de uma api externa a partir do nome fornecido, caso encontre."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",   
                    description = "Lista de filmes retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetMoviesByTitleResponse.class))
            ),

            @ApiResponse(
                    responseCode = "400",
                    description = "Os dados fornecidos estão inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class, example =
                            """
                            {
                                "instance": "/filmes/externo",
                                "status": 400,
                                "title": "A requisição contém campos inválidos",
                                "type": "http://localhost:8080/errors/invalid-argument",
                                "erros": {
                                    "title": "O título do filme é obrigatório"
                                }
                            }
                            """))
            )
    })
    public ResponseEntity<GetMoviesByTitleResponse> getExternalMovie(
            @NotBlank
            @Parameter(description = "Título do filme", example = "Piratas do Caribe")
            @RequestParam
            String title
    ) {
        GetMoviesByTitleResponse response = movieService.getExternal(title);

        return ResponseEntity
                .ok()
                .body(response);
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Filme encontrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetMoviesByTitleResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Campos fornecidos inválidos ou filme não identificado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class, example =
                            """
                            {
                                "instance": "/filmes",
                                "status": 400,
                                "title": "Falha durante execução da aplicação",
                                "type": "http://localhost:8080/errors/application",
                                "detail": "Não foi possível identificar um filme com este título"
                            }
                            """
                    ))
            )
    })
    @GetMapping()
    public ResponseEntity<GetMoviesByTitleResponse> get(
            @NotBlank(message = "O título é obrigatório")
            @Parameter(description = "Título do filme", example = "Minions")
            @RequestParam String title) {

        GetMoviesByTitleResponse response = movieService.get(title);

        return ResponseEntity.ok(response);
    }
}
