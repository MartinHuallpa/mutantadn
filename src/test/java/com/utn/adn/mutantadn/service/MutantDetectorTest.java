package com.utn.adn.mutantadn.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private MutantDetector mutantDetector;

    @BeforeEach
    void setUp() {
        mutantDetector = new MutantDetector();
    }

    @Test
    @DisplayName("Debe detectar mutante con secuencias horizontal y diagonal")
    void testMutantWithHorizontalAndDiagonalSequences() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con secuencias verticales")
    void testMutantWithVerticalSequences() {
        String[] dna = {
                "AAAAGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CACCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con diagonales cruzadas")
    void testMutantWithBothDiagonals() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con múltiples horizontales")
    void testMutantWithMultipleHorizontalSequences() {
        String[] dna = {
                "TTTTGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante en matriz 4x4 (tamaño mínimo)")
    void testSmallMatrixMutant() {
        String[] dna = {
                "AAAA",
                "CCCC",
                "TCAG",
                "GGTC"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con todas las bases iguales")
    void testAllSameBases() {
        String[] dna = {
                "AAAA",
                "AAAA",
                "AAAA",
                "AAAA"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("No debe detectar mutante con una sola secuencia")
    void testHumanWithOnlyOneSequence() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("No debe detectar mutante sin ninguna secuencia")
    void testHumanWithNoSequences() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("No debe detectar mutante en matriz sin coincidencias")
    void testHumanRandomMatrix() {
        String[] dna = {
                "ATGCTA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCGCTA",
                "TCACTG"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe lanzar excepción para ADN nulo")
    void testNullDna() {
        assertThrows(IllegalArgumentException.class, () -> mutantDetector.isMutant(null));
    }

    @Test
    @DisplayName("Debe lanzar excepción para ADN vacío")
    void testEmptyDna() {
        assertThrows(IllegalArgumentException.class, () -> mutantDetector.isMutant(new String[]{}));
    }

    @Test
    @DisplayName("Debe lanzar excepción para matriz no cuadrada")
    void testNonSquareMatrix() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT"
        };
        assertThrows(IllegalArgumentException.class, () -> mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe lanzar excepción si contiene caracteres inválidos")
    void testInvalidCharacters() {
        String[] dna = {
                "ATGCGA",
                "CAGTXC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertThrows(IllegalArgumentException.class, () -> mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe lanzar excepción si hay filas nulas")
    void testNullRow() {
        String[] dna = {
                "ATGCGA",
                null,
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertThrows(IllegalArgumentException.class, () -> mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar matriz NxN gigante rápido (Performance)")
    void testLargeMatrixPerformance() {
        int n = 100;
        String[] dna = new String[n];
        String row = "A".repeat(n);
        Arrays.fill(dna, row);

        long start = System.currentTimeMillis();
        boolean result = mutantDetector.isMutant(dna);
        long end = System.currentTimeMillis();

        assertTrue(result);
        assertTrue((end - start) < 200, "El algoritmo tardó más de 200ms");
    }
}