package ctw.screenscoreapi.Movies.application.controller;

import ctw.screenscoreapi.Movies.application.dtos.create.CreateMovieRequest;
import ctw.screenscoreapi.Movies.application.dtos.get.GetMovieResponse;
import ctw.screenscoreapi.Movies.application.dtos.get.GetListOfExternalMoviesResponse;
import ctw.screenscoreapi.Movies.application.dtos.get.GetListOfMoviesResponse;
import ctw.screenscoreapi.Movies.application.dtos.update.UpdateMovieRequest;
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
import jakarta.validation.constraints.Positive;
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
                    ref = "#/components/responses/400"
            ),
            @ApiResponse(
                    responseCode = "409",
                    ref = "#/components/responses/409"
            ),
            @ApiResponse (
                    responseCode = "500",
                    ref = "#/components/responses/500"
            )
    })
    public ResponseEntity<Void> create(@Valid @RequestBody CreateMovieRequest request) {
       movieService.create(request);

       return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/externo")
    @Operation(
            summary = "Retorna filmes de uma api externa a partir do título.",
            description = "Retorna uma lista de filmes de uma api externa a partir do título fornecido, caso encontre."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",   
                    description = "Lista de filmes retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetListOfExternalMoviesResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    ref = "#/components/responses/400"
            ),
            @ApiResponse(
                    responseCode = "500",
                    ref = "#/components/responses/500"
            )
    })
    public ResponseEntity<GetListOfExternalMoviesResponse> getExternalMovie(
            @NotBlank
            @Parameter(description = "Título do filme", example = "Piratas do Caribe")
            @RequestParam
            String title
    ) {
        GetListOfExternalMoviesResponse response = movieService.getExternal(title);

        return ResponseEntity
                .ok()
                .body(response);
    }
    @Operation(
            summary = "Retorna um filme cadastrado no sistema a partir do ID.",
            description = "Retorna os detalhes de um filme previamente cadastrado no sistema, com base no ID informado na URL."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Filme encontrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetMovieResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    ref = "#/components/responses/400"
            ),
            @ApiResponse(
                    responseCode = "404",
                    ref = "#/components/responses/404"
            ),
            @ApiResponse(
                    responseCode = "500",
                    ref = "#/components/responses/500"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetMovieResponse> getMovieById(
            @Positive(message = "O ID deve ser um número positivo")
            @Parameter(description = "Número identificador do filme", example = "4")
            @PathVariable
            long id
    ) {

        GetMovieResponse response = movieService.getById(id);

        return ResponseEntity.ok(response);
    }
    @Operation(
            summary = "Retorna filmes cadastrados no sistema",
            description = "Retorna uma lista de filmes do sistema. Se o parâmetro 'title' for fornecido, retorna apenas os filmes cujo título corresponda; caso contrário, retorna todos os filmes cadastrados."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de filmes retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetListOfMoviesResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    ref = "#/components/responses/400"
            ),
            @ApiResponse(
                    responseCode = "404",
                    ref = "#/components/responses/404"
            ),
            @ApiResponse(
                    responseCode = "500",
                    ref = "#/components/responses/500"
            )
    })
    @GetMapping()
    public ResponseEntity<GetListOfMoviesResponse> getMovies(
            @Parameter(description = "Título do filme", example = "Minions", required = false)
            @RequestParam(required = false)
            String title
    ) {

        GetListOfMoviesResponse response = movieService.getMoviesWithFilters(title);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Deleta filmes através do ID",
            description = "Deleta filmes cadastrados no sistema a partir do ID enviado como parâmetro na URL"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Filme deletado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    ref = "#/components/responses/400"
            ),
            @ApiResponse(
                    responseCode = "404",
                    ref = "#/components/responses/404"
            ),
            @ApiResponse(
                    responseCode = "500",
                    ref = "#/components/responses/500"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do filme", example = "256")
            @Positive(message = "O id do filme deve ser um número positivo")
            @PathVariable
            long id
    ) {
        movieService.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody UpdateMovieRequest request) {
        movieService.update(id, request);

        return ResponseEntity.noContent().build();
    }
}
