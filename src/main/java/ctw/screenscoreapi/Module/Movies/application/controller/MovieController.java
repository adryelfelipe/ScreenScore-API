package ctw.screenscoreapi.Module.Movies.application.controller;

import ctw.screenscoreapi.Module.Movies.application.dtos.create.CreateMovieRequest;
import ctw.screenscoreapi.Module.Movies.application.dtos.get.GetExternalMovieResponse;
import ctw.screenscoreapi.Module.Movies.application.dtos.get.GetMovieResponse;
import ctw.screenscoreapi.Module.Movies.application.dtos.get.GetListOfExternalMoviesResponse;
import ctw.screenscoreapi.Module.Movies.application.dtos.get.GetListOfMoviesResponse;
import ctw.screenscoreapi.Module.Movies.application.dtos.update.UpdateMovieRequest;
import ctw.screenscoreapi.Module.Movies.application.service.MovieService;
import ctw.screenscoreapi.Module.Movies.domain.enums.Genre;
import ctw.screenscoreapi.Share.aop.authentication.ToAuthenticate;
import ctw.screenscoreapi.Share.aop.authorization.ToAuthorize;
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
import jdk.jfr.ContentType;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@CrossOrigin
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
                    responseCode = "403",
                    ref = "#/components/responses/403"
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
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ToAuthenticate
    @ToAuthorize
    public ResponseEntity<Void> create(
            @Parameter(description = "Dados do filme", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
            @RequestPart() CreateMovieRequest data, MultipartFile file) throws IOException {

       long movieId = movieService.create(data, file);

       return ResponseEntity
               .created(URI.create("/filmes/" + movieId))
               .build();
    }

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
                    responseCode = "403",
                    ref = "#/components/responses/403"
            ),
            @ApiResponse(
                    responseCode = "500",
                    ref = "#/components/responses/500"
            ),
            @ApiResponse(
                    responseCode = "502",
                    ref = "#/components/responses/502"
            )
    })
    @GetMapping("/externos")
    @ToAuthenticate
    @ToAuthorize
    public ResponseEntity<GetListOfExternalMoviesResponse> getExternalMoviesByTitle(
            @NotBlank()
            @Parameter(description = "Título do filme", example = "Piratas do Caribe", required = true)
            @RequestParam
            String title
    ) {
        GetListOfExternalMoviesResponse response = movieService.getExternalByTitle(title);

        return ResponseEntity
                .ok()
                .body(response);
    }

    @Operation(
            summary = "Retorna um filme de uma api externa a partir do id.",
            description = "Retorna um filme de uma api externa a partir do id fornecido, caso encontre."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Filme retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetExternalMovieResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    ref = "#/components/responses/400"
            ),
            @ApiResponse(
                    responseCode = "403",
                    ref = "#/components/responses/403"
            ),
            @ApiResponse(
                    responseCode = "500",
                    ref = "#/components/responses/500"
            ),
            @ApiResponse(
                    responseCode = "502",
                    ref = "#/components/responses/502"
            )
    })
    @GetMapping("/externos/{id}")
    @ToAuthenticate
    @ToAuthorize
    public ResponseEntity<GetExternalMovieResponse> getExternalMoviesById(
            @Positive(message = "O ID deve ser um número positivo")
            @Parameter(description = "Número identificador do filme", example = "25", required = true)
            @PathVariable
            long id
    ) {
        GetExternalMovieResponse response = movieService.getExternalById(id);

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
    @ToAuthenticate
    public ResponseEntity<GetMovieResponse> getMovieById(
            @Positive(message = "O ID deve ser um número positivo")
            @Parameter(description = "Número identificador do filme", example = "4", required = true)
            @PathVariable
            long id
    ) {

        GetMovieResponse response = movieService.getById(id);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Retorna filmes cadastrados no sistema",
            description =
            """
            Retorna uma lista de filmes disponíveis no sistema.
            
            - Se o parâmetro 'title' for fornecido, retorna apenas os filmes cujo título contenha a string informada (busca case-insensitive).
            - Se o parâmetro 'genre' for fornecido, retorna apenas os filmes que possuam **pelo menos um** dos gêneros informados.
            - Se ambos os parâmetros forem fornecidos, aplica ambos os filtros combinados.
            - Se nenhum parâmetro for informado, retorna todos os filmes cadastrados.
            
            Cada filme retornado inclui informações como título, título original, idioma original, data de lançamento, poster, overview e lista de gêneros associados.
            """
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
                    responseCode = "500",
                    ref = "#/components/responses/500"
            )
    })
    @GetMapping()
    @ToAuthenticate
    public ResponseEntity<GetListOfMoviesResponse> getMovies(
            @Parameter(description = "Título do filme", example = "Minions", required = false)
            @RequestParam(required = false)
            String title,

            @Parameter(description = "Gêneros do filme", required = false)
            @RequestParam(required = false)
            List<Genre> genre
    ) {

        GetListOfMoviesResponse response = movieService.getMovies(title, genre);

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
    @ToAuthenticate
    @ToAuthorize
    public ResponseEntity<Void> delete(
            @Positive(message = "O ID deve ser um número positivo")
            @Parameter(description = "ID do filme", example = "256", required = true)
            @PathVariable
            long id
    ) {
        movieService.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @Operation(
            summary = "Atualiza filmes através do ID",
            description = "Atualiza filmes cadastrados no sistema a partir do ID enviado como parâmetro na URL."
    )
    @ApiResponses({
            @ApiResponse(
              responseCode = "204",
              description = "Filme atualizado com sucesso"
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
                    responseCode = "409",
                    ref = "#/components/responses/409"
            ),
            @ApiResponse(
                    responseCode = "422",
                    ref = "#/components/responses/422"
            ),
            @ApiResponse(
                    responseCode = "500",
                    ref = "#/components/responses/500"
            )
    })
    @PutMapping("/{id}")
    @ToAuthenticate
    @ToAuthorize
    public ResponseEntity<Void> update(
            @Positive(message = "O ID deve ser um número positivo")
            @Parameter(description = "Número identificador do filme", example = "4", required = true)
            @PathVariable
            long id,

            @Parameter(description = "Dados do filme", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
            @RequestPart
            @Valid
            UpdateMovieRequest data,

            MultipartFile file
    ) throws IOException {
        movieService.update(id, data, file);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Retorna os 10 filmes mais bem avaliados",
            description = "Retorna os 10 filmes mais bem avaliados pelos usuários da plataforma"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetListOfMoviesResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    ref = "#/components/responses/500"
            )
    })
    @GetMapping("/top10")
    @ToAuthenticate@Schema(example = "1")
    public ResponseEntity<GetListOfMoviesResponse> getTop10Movies() {
        GetListOfMoviesResponse movies = movieService.getTop10Movies();

        return ResponseEntity.ok(movies);
    }
}
