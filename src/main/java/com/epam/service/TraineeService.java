package com.epam.service;

import com.epam.dto.trainee.TraineeWithTraining;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;

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
    void activateTrainee(Integer traineeId);
    void deactivateTrainee(Integer traineeId);
    boolean areUsernameAndPasswordMatching(String username, String password);

    List<Trainer> getTrainersByTraineeId(Integer id);

    List<Trainer> getNotAssignedTrainers(String username);

    List<Training> getTraineeTrainingsList(TraineeWithTraining traineeWithTraining);
}
