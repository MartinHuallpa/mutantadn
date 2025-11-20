package com.utn.adn.mutantadn.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "DTO de salida con las estadísticas de verificaciones de ADN.")
public record StatsResponse(
        @Schema(description = "Cantidad total de mutantes detectados", example = "40")
        @JsonProperty("count_mutant_dna")
        long countMutantDna,

        @Schema(description = "Cantidad total de humanos detectados", example = "100")
        @JsonProperty("count_human_dna")
        long countHumanDna,

        @Schema(description = "Ratio de mutantes sobre el total (mutantes/humanos)", example = "0.4")
        double ratio
) {}