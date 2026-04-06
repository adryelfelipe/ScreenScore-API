package ctw.screenscoreapi.Module.Avaliations.application.controller;

import ctw.screenscoreapi.Module.Avaliations.application.dtos.create.CreateAvaliationRequest;
import ctw.screenscoreapi.Module.Avaliations.application.dtos.get.GetAvaliationResponse;
import ctw.screenscoreapi.Module.Avaliations.application.dtos.get.GetListOfAvaliationResponse;
import ctw.screenscoreapi.Module.Avaliations.application.dtos.update.UpdateAvaliationRequest;
import ctw.screenscoreapi.Module.Avaliations.application.service.AvaliationService;
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
import jakarta.validation.constraints.Positive;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "Avaliações", description = "Endpoints para gerenciamento de avaliações")
@RestController
@RequestMapping("/avaliacoes")
public class AvaliationController {
    private AvaliationService avaliationService;

    public AvaliationController(AvaliationService avaliationService) {
        this.avaliationService = avaliationService;
    }

    @Operation(
            summary = "Cadastra uma avaliação no sistema",
            description = "Registra a nota e o comentário de um usuário para um filme existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Avaliação cadastrada com sucesso!"
            ),
            @ApiResponse(
                    responseCode = "400",
                    ref = "#/components/responses/400"
            ),
            @ApiResponse(
                    responseCode = "401",
                    ref = "#/components/responses/401"
            ),
            @ApiResponse(
                    responseCode = "500",
                    ref = "#/components/responses/500"
            )
    })
    @PostMapping
    @ToAuthenticate
    public ResponseEntity<Void> create(@Valid @RequestBody CreateAvaliationRequest request) {
        long id = avaliationService.create(request);

        return ResponseEntity
                .created(URI.create("/avaliacoes/" + id))
                .build();
    }

    @Operation(
            summary = "Deleta avaliações através do ID",
            description = "Deleta avaliações cadastradas no sistema a partir do número identificador enviado como parâmetro na URL"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Avaliação deletada com sucesso!"
            ),
            @ApiResponse(
                    responseCode = "400",
                    ref = "#/components/responses/400"
            ),
            @ApiResponse(
                    responseCode = "401",
                    ref = "#/components/responses/401"
            ),
            @ApiResponse(
                    responseCode = "403",
                    ref = "#/components/responses/403"
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
    public ResponseEntity<Void> delete(
            @Positive(message = "O ID deve ser um número positivo")
            @Parameter(description = "Número identificador da avaliação", example = "4", required = true)
            @PathVariable Long id) {
        avaliationService.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @Operation(
            summary = "Retorna uma avaliação cadastrada no sistema a partir do ID.",
            description = "Retorna os detalhes de uma avaliação previamente cadastrada no sistema, com base no ID informado na URL."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetAvaliationResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    ref = "#/components/responses/400"
            ),
            @ApiResponse(
                    responseCode = "401",
                    ref = "#/components/responses/401"
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
    public ResponseEntity<GetAvaliationResponse> getById(
            @Positive(message = "O ID deve ser um número positivo")
            @Parameter(description = "Número identificador da avaliação", example = "4", required = true)
            @PathVariable Long id) {
        GetAvaliationResponse resposne = avaliationService.getById(id);

        return ResponseEntity
                .ok()
                .body(resposne);
    }

    @Operation(
            summary = "Retorna filmes cadastrados no sistema",
            description = "Retorna uma lista de filmes disponíveis no sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetListOfAvaliationResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    ref = "#/components/responses/401"
            ),
            @ApiResponse(
                    responseCode = "403",
                    ref = "#/components/responses/403"
            ),
            @ApiResponse(
                    responseCode = "500",
                    ref = "#/components/responses/500"
            )
    })
    @GetMapping
    @ToAuthenticate
    @ToAuthorize
    public ResponseEntity<GetListOfAvaliationResponse> getAll() {
        GetListOfAvaliationResponse response = avaliationService.getAll();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Avaliação alterada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    ref = "#/components/responses/400"
            ),
            @ApiResponse(
                    responseCode = "401",
                    ref = "#/components/responses/401"
            ),
            @ApiResponse(
                    responseCode = "403",
                    ref = "#/components/responses/403"
            ),
            @ApiResponse(
                    responseCode = "404",
                    ref = "#/components/responses/404"
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
    public ResponseEntity<Void> update(@Valid @RequestBody UpdateAvaliationRequest request, @PathVariable Long id) {
        avaliationService.update(request, id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
