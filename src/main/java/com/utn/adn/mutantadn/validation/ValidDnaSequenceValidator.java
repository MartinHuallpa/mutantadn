package com.utn.adn.mutantadn.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

    private static final Pattern VALID_CHARACTERS = Pattern.compile("^[ATCG]+$");

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        if (dna == null) return false; // Nulo

        int n = dna.length;
        if (n == 0) return false; // Vacío

        for (String row : dna) {
            if (row == null) return false; // Fila nula
            if (row.length() != n) return false; // No es cuadrada (NxN)
            if (!VALID_CHARACTERS.matcher(row).matches()) return false; // Caracteres inválidos
        }

        return true;
    }
}