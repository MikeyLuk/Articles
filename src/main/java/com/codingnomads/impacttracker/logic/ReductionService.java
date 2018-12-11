package com.codingnomads.impacttracker.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReductionService {

    private ReductionRepository reductionRepository;

    @Autowired
    public ReductionService(ReductionRepository reductionRepository) {
        this.reductionRepository = reductionRepository;
    }

    public List<Reduction> getAllReductions () {
        return reductionRepository.getAllReductions();
    }

}
