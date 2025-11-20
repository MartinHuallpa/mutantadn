package com.utn.adn.mutantadn.controller;

import com.utn.adn.mutantadn.dto.DnaRequest;
import com.utn.adn.mutantadn.dto.StatsResponse;
import com.utn.adn.mutantadn.service.MutantService;
import com.utn.adn.mutantadn.service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mutant")
@RequiredArgsConstructor
@Tag(name = "Mutant Detector", description = "API para la detección de mutantes y estadísticas")
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    @Operation(summary = "Detectar si un humano es mutante", description = "Analiza la secuencia de ADN enviada y determina si corresponde a un mutante.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Es Mutante"),
            @ApiResponse(responseCode = "403", description = "No es Mutante (Es Humano)"),
            @ApiResponse(responseCode = "400", description = "ADN Inválido (no cuadrado, caracteres erróneos o nulo)")
    })
    @PostMapping

    public ResponseEntity<Void> checkMutant(@Valid @RequestBody DnaRequest dnaRequest) {
        boolean isMutant = mutantService.analyzeDna(dnaRequest.dna());
        if (isMutant) {
            return ResponseEntity.ok().build(); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden
        }
    }
}