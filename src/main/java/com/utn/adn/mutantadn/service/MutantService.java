package com.utn.adn.mutantadn.service;

import com.utn.adn.mutantadn.entity.DnaRecord;
import com.utn.adn.mutantadn.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MutantService {

    private final MutantDetector mutantDetector;
    private final DnaRecordRepository dnaRecordRepository;

    @Transactional
    public boolean analyzeDna(String[] dna) {

        String dnaHash = Integer.toHexString(Arrays.deepHashCode(dna));


        Optional<DnaRecord> existing = dnaRecordRepository.findByDnaHash(dnaHash);
        if (existing.isPresent()) {
            return existing.get().isMutant();
        }

        boolean isMutant = mutantDetector.isMutant(dna);

        DnaRecord record = DnaRecord.builder()
                .dnaHash(dnaHash)
                .isMutant(isMutant)
                .build();

        dnaRecordRepository.save(record);

        return isMutant;
    }
}