package com.utn.adn.mutantadn.service;

import org.springframework.stereotype.Component;
import java.io.Serializable;

@Component
public class MutantDetector implements Serializable {


    private static final int SEQUENCE_LENGTH = 4;
    private static final int MUTANT_SEQUENCE_COUNT = 2;
    private static final String VALID_CHARS = "ATCG";

    public boolean isMutant(String[] dna) {

        if (dna == null || dna.length == 0) {
            throw new IllegalArgumentException("El ADN no puede ser nulo ni vacío");
        }

        int n = dna.length;
        char[][] matrix = new char[n][n];

        for (int i = 0; i < n; i++) {
            String row = dna[i];

            if (row == null) {
                throw new IllegalArgumentException("La fila " + i + " del ADN no puede ser nula");
            }

            if (row.length() != n) {
                throw new IllegalArgumentException("El ADN debe ser una matriz cuadrada NxN");
            }


            for (int j = 0; j < n; j++) {
                char c = Character.toUpperCase(row.charAt(j));
                if (VALID_CHARS.indexOf(c) == -1) {
                    throw new IllegalArgumentException("Carácter inválido en ADN: " + c);
                }
                matrix[i][j] = c;
            }
        }

        int sequenceCount = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (j <= n - SEQUENCE_LENGTH) {
                    if (checkHorizontal(matrix, i, j)) {
                        sequenceCount++;
                        if (sequenceCount >= MUTANT_SEQUENCE_COUNT) return true;
                    }
                }

                if (i <= n - SEQUENCE_LENGTH) {
                    if (checkVertical(matrix, i, j)) {
                        sequenceCount++;
                        if (sequenceCount >= MUTANT_SEQUENCE_COUNT) return true;
                    }
                }

                if (i <= n - SEQUENCE_LENGTH && j <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonal(matrix, i, j)) {
                        sequenceCount++;
                        if (sequenceCount >= MUTANT_SEQUENCE_COUNT) return true;
                    }
                }

                if (i >= SEQUENCE_LENGTH - 1 && j <= n - SEQUENCE_LENGTH) {
                    if (checkContraDiagonal(matrix, i, j)) {
                        sequenceCount++;
                        if (sequenceCount >= MUTANT_SEQUENCE_COUNT) return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkHorizontal(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return base == matrix[row][col+1] &&
                base == matrix[row][col+2] &&
                base == matrix[row][col+3];
    }

    private boolean checkVertical(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return base == matrix[row+1][col] &&
                base == matrix[row+2][col] &&
                base == matrix[row+3][col];
    }

    private boolean checkDiagonal(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return base == matrix[row+1][col+1] &&
                base == matrix[row+2][col+2] &&
                base == matrix[row+3][col+3];
    }

    private boolean checkContraDiagonal(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return base == matrix[row-1][col+1] &&
                base == matrix[row-2][col+2] &&
                base == matrix[row-3][col+3];
    }
}