package com.utn.adn.mutantadn.dto;

import com.utn.adn.mutantadn.validation.ValidDnaSequence;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Schema(description = "DTO de entrada para analizar un ADN.")
public record DnaRequest(
        @Schema(
                description = "Array de Strings que representa la matriz de ADN (NxN)",
                example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]"
        )
        @NotNull(message = "El ADN no puede ser nulo")
        @ValidDnaSequence
        String[] dna
) implements Serializable {}