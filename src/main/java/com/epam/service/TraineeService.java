package com.epam.service;

import com.epam.model.Trainee;

import java.util.List;
import java.util.Optional;

public interface TraineeService {

    Trainee createTrainee(Trainee trainee);
    Optional<Trainee> updateTrainee(Trainee trainee);
    Optional<Trainee> getTraineeById(Integer traineeId);
    Optional<Trainee> getTraineeByUsername(String username);
    List<Trainee> getAllTrainees();
    boolean deleteTrainee(Integer traineeId);
    boolean deleteTraineeByUsername(String username);
    boolean changePassword(Integer traineeId, String currentPassword, String newPassword);
    void activateDeactivateTrainee(Integer traineeId, boolean activate);
    boolean areUsernameAndPasswordMatching(String username, String password);
}
