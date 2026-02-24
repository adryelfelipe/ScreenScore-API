package ctw.screenscoreapi.Movies.application.controller;

import ctw.screenscoreapi.Movies.application.dtos.create.CreateMovieRequest;
import ctw.screenscoreapi.Movies.application.dtos.get.GetExternalMovieRequest;
import ctw.screenscoreapi.Movies.application.dtos.get.GetExternalMovieResponse;
import ctw.screenscoreapi.Movies.application.dtos.get.GetMovieResponse;
import ctw.screenscoreapi.Movies.application.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    @Operation (
            summary = "Retorna filmes de uma api externa.",
            description = "Retorna uma lista de filmes de uma api externa a partir do nome fornecido, caso encontre."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de filmes retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetExternalMovieResponse.class, example =
                            """
                            {
                                    "movies": [
                                            {
                                                "title": "Minions",
                                                "originalLanguage": "en",
                                                "originalTitle": "Minions",
                                                "adult": false,
                                                "releaseDate": "2015-06-17",
                                                "posterImage": "/caq9Xi6b1sZNREfzFBO2tRIBzWn.jpg",
                                                "overview": "Seres amarelos milenares, os minions têm uma missão: servir os maiores vilões.", 
                                                "genres": [
                                                    "FAMILIA",
                                                    "ANIMACAO",
                                                    "AVENTURA",
                                                    "COMEDIA"
                                                ]
                                            },
                                            {
                                                "title": "Minions 2: A Origem de Gru",
                                                "originalLanguage": "en",
                                                "originalTitle": "Minions: The Rise of Gru",
                                                "adult": false,
                                                "releaseDate": "2022-06-29",
                                                "posterImage": "/iTP3mMw0AoqmScYzDoMmYeKxYe.jpg",
                                                "overview": "A continuação das aventuras dos Minions, sempre em busca de um líder tirânico. Dessa vez, eles ajudam um Gru ainda criança, descobrindo como ser vilão.",
                                                "genres": [
                                                    "FAMILIA",
                                                    "COMEDIA",
                                                    "AVENTURA",
                                                    "ANIMACAO",
                                                    "FICCAO_CIENTIFICA",
                                                    "FANTASIA"
                                                ]
                                           }
                                     ]       
                            }        
                            """
                    ))
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
    public ResponseEntity<GetExternalMovieResponse> getExternalMovie(@Valid GetExternalMovieRequest request) {
        GetExternalMovieResponse response = movieService.getExternal(request);

        return ResponseEntity
                .ok()
                .body(response);
    }
}
