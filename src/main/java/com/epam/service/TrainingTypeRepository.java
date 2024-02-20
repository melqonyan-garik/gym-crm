package com.epam.service;

import com.epam.model.TrainingType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainingTypeRepository extends JpaRepository<TrainingType,Integer> {
    Optional<TrainingType> findByTrainingTypeName(String trainingTypeName);
}
