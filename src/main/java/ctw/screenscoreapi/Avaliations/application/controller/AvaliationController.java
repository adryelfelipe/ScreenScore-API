package ctw.screenscoreapi.Avaliations.application.controller;

import ctw.screenscoreapi.Avaliations.application.dtos.create.CreateAvaliationRequest;
import ctw.screenscoreapi.Avaliations.application.dtos.get.GetAvaliationResponse;
import ctw.screenscoreapi.Avaliations.application.service.AvaliationService;
import ctw.screenscoreapi.Share.aop.authentication.ToAuthenticate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    public ResponseEntity<Void> delete(@PathVariable Long id) {
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
    public ResponseEntity<GetAvaliationResponse> getById(@PathVariable Long id) {
        GetAvaliationResponse resposne = avaliationService.getById(id);

        return ResponseEntity
                .ok()
                .body(resposne);
    }
}
