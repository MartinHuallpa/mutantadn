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
@RequestMapping("/")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Mutant Detector", description = "API para la detección de mutantes y estadísticas")
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    @Operation(summary = "Detectar si un humano es mutante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Es Mutante"),
            @ApiResponse(responseCode = "403", description = "No es Mutante (Es Humano)"),
            @ApiResponse(responseCode = "400", description = "ADN Inválido")
    })
    @PostMapping("/mutant")
    public ResponseEntity<Void> checkMutant(@Valid @RequestBody DnaRequest dnaRequest) {
        boolean isMutant = mutantService.analyzeDna(dnaRequest.dna());
        if (isMutant) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(summary = "Obtener estadísticas")
    @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas correctamente")
    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats() {
        return ResponseEntity.ok(statsService.getStats());
    }
}