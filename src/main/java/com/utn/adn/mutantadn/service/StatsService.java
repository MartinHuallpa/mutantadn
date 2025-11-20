package com.utn.adn.mutantadn.service;

import com.utn.adn.mutantadn.dto.StatsResponse;
import com.utn.adn.mutantadn.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final DnaRecordRepository repository;

    public StatsResponse getStats() {
        long countMutant = repository.countByIsMutant(true);
        long countHuman = repository.countByIsMutant(false);

        double ratio = 0.0;
        if (countHuman > 0) {
            ratio = (double) countMutant / countHuman;
        } else if (countMutant > 0) {
            ratio = 1.0;
        }

        return StatsResponse.builder()
                .countMutantDna(countMutant)
                .countHumanDna(countHuman)
                .ratio(ratio)
                .build();
    }
}