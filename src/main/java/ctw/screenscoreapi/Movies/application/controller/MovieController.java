package ctw.screenscoreapi.Movies.application.controller;

import ctw.screenscoreapi.Movies.application.dtos.create.CreateMovieRequest;
import ctw.screenscoreapi.Movies.application.dtos.get.GetMoviesByTitleResponse;
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
                    ref = "#/components/responses/Movie_400"
            ),
            @ApiResponse(
                    responseCode = "409",
                    ref = "#/components/responses/Movie_409"
            ),
            @ApiResponse (
                    responseCode = "500",
                    ref = "#/components/responses/Movie_500"
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
                    ref = "#/components/responses/Movie_400"
            ),
            @ApiResponse(
                    responseCode = "500",
                    ref = "#/components/responses/Movie_500"
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
                    ref = "#/components/responses/Movie_400"
            ),
            @ApiResponse(
                    responseCode = "404",
                    ref = "#/components/responses/Movie_404"
            ),
            @ApiResponse(
                    responseCode = "500",
                    ref = "#/components/responses/Movie_500"
            )
    })
    @GetMapping()
    public ResponseEntity<GetMoviesByTitleResponse> get(
            @NotBlank(message = "O título é obrigatório")
            @Parameter(description = "Título do filme", example = "Minions")
            @RequestParam
            String title
    ) {

        GetMoviesByTitleResponse response = movieService.get(title);

        return ResponseEntity.ok(response);
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    ref = "#/components/responses/Movie_400"
            ),
            @ApiResponse(
                    responseCode = "404",
                    ref = "#/components/responses/Movie_404"
            ),
            @ApiResponse(
                    responseCode = "500",
                    ref = "#/components/responses/Movie_500"
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
}
