package com.epam.service;

import com.epam.model.Trainee;

import java.util.List;

public interface TraineeService {

    void createTrainee(Trainee trainee);
    boolean updateTrainee(Trainee trainee);
    void deleteTrainee(Integer traineeId);
    Trainee getTraineeById(Integer traineeId);
    List<Trainee> getAllTrainees();
    boolean changePassword(Integer traineeId, String currentPassword, String newPassword);
    void activateDeactivateTrainee(Integer traineeId, boolean activate);
    void deleteTraineeByUsername(String username);

    void checkUsernameAndPasswordMatching(String username, String password);
}
