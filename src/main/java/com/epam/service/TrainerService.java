package com.epam.service;

import com.epam.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerService {
    Trainer createTrainer(Trainer trainer);
    Optional<Trainer> updateTrainer(Trainer trainer);
    Optional<Trainer> getTrainerById(Integer trainerId);
    List<Trainer> getAllTrainer();
    boolean deleteTrainer(Integer trainerId);
    boolean changePassword(Integer trainerId, String currentPassword, String newPassword);
    void activateDeactivateTrainer(Integer trainerId, boolean activate);
    boolean areUsernameAndPasswordMatching(String username, String password);
}
