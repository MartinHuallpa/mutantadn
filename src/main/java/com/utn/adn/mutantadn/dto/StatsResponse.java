package com.utn.adn.mutantadn.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "DTO de salida con las estadísticas.")
public record StatsResponse(
        @JsonProperty("count_mutant_dna")
        long countMutantDna,

        @JsonProperty("count_human_dna")
        long countHumanDna,

        double ratio
) {}