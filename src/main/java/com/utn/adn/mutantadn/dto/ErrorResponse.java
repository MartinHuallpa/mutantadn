package com.utn.adn.mutantadn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "DTO para devolver errores de la API.")
public record ErrorResponse(
        @Schema(description = "Mensaje descriptivo del error", example = "La matriz no es cuadrada")
        String message,

        @Schema(description = "Código de estado HTTP", example = "400")
        int status
) {}