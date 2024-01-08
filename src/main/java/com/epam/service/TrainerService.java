package com.epam.service;

import com.epam.dto.trainer.TrainerWithTraining;
import com.epam.model.Trainer;
import com.epam.model.Training;

import java.util.List;
import java.util.Optional;

public interface TrainerService {
    Trainer createTrainer(Trainer trainer);
    Optional<Trainer> updateTrainer(Trainer trainer);
    Optional<Trainer> getTrainerById(Integer trainerId);
    List<Trainer> getAllTrainer();
    boolean deleteTrainer(Integer trainerId);
    boolean changePassword(Integer trainerId, String currentPassword, String newPassword);
    void activateTrainer(Integer trainerId);
    void deactivateTrainer(Integer trainerId);
    boolean areUsernameAndPasswordMatching(String username, String password);

    Optional<Trainer> getTrainerByUsername(String username);

    List<Training> getTrainerTrainingsList(TrainerWithTraining trainerWithTraining);
}
