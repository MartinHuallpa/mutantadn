package com.utn.adn.mutantadn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.adn.mutantadn.dto.DnaRequest;
import com.utn.adn.mutantadn.dto.StatsResponse;
import com.utn.adn.mutantadn.service.MutantService;
import com.utn.adn.mutantadn.service.StatsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MutantController.class)
class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // ObjectMapper es necesario para convertir objetos a JSON manualmente en los tests
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MutantService mutantService;

    @MockBean
    private StatsService statsService;

    @Test
    @DisplayName("POST /mutant debe retornar 200 OK si es mutante")
    void testCheckMutant_IsMutant() throws Exception {
        String[] dna = {"AAAA", "CCCC", "TCAG", "GGTC"};
        DnaRequest request = new DnaRequest(dna);

        // Importante: Usar objectMapper para asegurar que el JSON está bien formado
        String jsonRequest = objectMapper.writeValueAsString(request);

        when(mutantService.analyzeDna(any())).thenReturn(true);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print()) // Imprime detalles si falla
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /mutant debe retornar 403 Forbidden si es humano")
    void testCheckMutant_IsHuman() throws Exception {
        String[] dna = {"AAAT", "CCCG", "TCAG", "GGTC"};
        DnaRequest request = new DnaRequest(dna);
        String jsonRequest = objectMapper.writeValueAsString(request);

        when(mutantService.analyzeDna(any())).thenReturn(false);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("POST /mutant debe retornar 400 Bad Request si el ADN es inválido")
    void testCheckMutant_InvalidDna() throws Exception {
        // Matriz no cuadrada (3x4)
        String[] dna = {"AAAA", "CCCC", "TCAG"};
        DnaRequest request = new DnaRequest(dna);
        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /stats debe retornar las estadísticas correctamente")
    void testGetStats() throws Exception {

        StatsResponse response = new StatsResponse(40, 100, 0.4);

        when(statsService.getStats()).thenReturn(response);

        mockMvc.perform(get("/stats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(40))
                .andExpect(jsonPath("$.count_human_dna").value(100))
                .andExpect(jsonPath("$.ratio").value(0.4));
    }
}