package com.epam.service;

import com.epam.model.Trainer;

import java.util.List;

public interface TrainerService {
    void createTrainer(Trainer trainer);
    boolean updateTrainer(Trainer trainer);
    void deleteTrainer(Integer trainerId);
    Trainer getTrainerById(Integer trainerId);
    List<Trainer> getAllTrainer();
    boolean changePassword(Integer trainerId, String currentPassword, String newPassword);
    void activateDeactivateTrainer(Integer trainerId, boolean activate);
    void checkUsernameAndPasswordMatching(String username, String password);
}
