package com.utn.adn.mutantadn.service;

import com.utn.adn.mutantadn.dto.StatsResponse;
import com.utn.adn.mutantadn.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private StatsService statsService;

    @Test
    @DisplayName("Debe calcular estadísticas correctamente")
    void testGetStatsWithData() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(40L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(100L);

        StatsResponse stats = statsService.getStats();

        assertEquals(40, stats.countMutantDna());
        assertEquals(100, stats.countHumanDna());
        assertEquals(0.4, stats.ratio(), 0.001);  // 40/100 = 0.4
    }

    @Test
    @DisplayName("Debe retornar ratio 1.0 cuando no hay humanos (solo mutantes)")
    void testGetStatsWithNoHumans() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(10L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse stats = statsService.getStats();

        assertEquals(10, stats.countMutantDna());
        assertEquals(0, stats.countHumanDna());
        assertEquals(1.0, stats.ratio()); // Evita división por cero
    }

    @Test
    @DisplayName("Debe retornar ratio 0 cuando no hay datos")
    void testGetStatsWithNoData() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(0L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse stats = statsService.getStats();

        assertEquals(0, stats.countMutantDna());
        assertEquals(0, stats.countHumanDna());
        assertEquals(0.0, stats.ratio());
    }
}