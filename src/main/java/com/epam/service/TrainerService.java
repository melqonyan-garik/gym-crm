package com.epam.service;

import com.epam.model.Trainer;

import java.util.List;

public interface TrainerService {
    void createTrainer(Trainer trainee);
    void updateTrainer(Trainer trainee);
    void deleteTrainer(Integer traineeId);
    Trainer getTrainerById(Integer traineeId);
    List<Trainer> getAllTrainer();
}
