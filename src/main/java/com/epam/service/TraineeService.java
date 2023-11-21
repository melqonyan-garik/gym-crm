package com.epam.service;

import com.epam.model.Trainee;

import java.util.List;

public interface TraineeService {

    void createTrainee(Trainee trainee);
    void updateTrainee(Trainee trainee);
    void deleteTrainee(Integer traineeId);
    Trainee getTraineeById(Integer traineeId);
    List<Trainee> getAllTrainees();
}
