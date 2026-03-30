package ctw.screenscoreapi.Avaliations.application.controller;

import ctw.screenscoreapi.Avaliations.application.dtos.create.CreateAvaliationRequest;
import ctw.screenscoreapi.Avaliations.application.service.AvaliationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliationController {
    private AvaliationService avaliationService;

    public AvaliationController(AvaliationService avaliationService) {
        this.avaliationService = avaliationService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateAvaliationRequest request) {
        long id = avaliationService.create(request);

        return ResponseEntity
                .created(URI.create("/avaliacoes/" + id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        avaliationService.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
