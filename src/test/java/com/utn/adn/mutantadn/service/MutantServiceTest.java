package com.utn.adn.mutantadn.service;

import com.utn.adn.mutantadn.entity.DnaRecord;
import com.utn.adn.mutantadn.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutantServiceTest {

    @Mock
    private MutantDetector mutantDetector;

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private MutantService mutantService;

    @Test
    void testAnalyzeDna_Mutant_NotCached() {

        String[] dna = {"AAAA", "CCCC", "TCAG", "GGTC"};

        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(dna)).thenReturn(true);

        boolean result = mutantService.analyzeDna(dna);

        assertTrue(result);
        verify(mutantDetector, times(1)).isMutant(dna);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    void testAnalyzeDna_Human_NotCached() {

        String[] dna = {"ACTG", "CAGT", "TTAT", "AGAC"};

        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(dna)).thenReturn(false);

        boolean result = mutantService.analyzeDna(dna);

        assertFalse(result);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    void testAnalyzeDna_Cached() {
        String[] dna = {"AAAA", "CCCC", "TCAG", "GGTC"};

        DnaRecord existingRecord = new DnaRecord();
        existingRecord.setMutant(true);
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.of(existingRecord));

        boolean result = mutantService.analyzeDna(dna);

        assertTrue(result);
        verify(mutantDetector, never()).isMutant(any());
        verify(dnaRecordRepository, never()).save(any());
    }
}