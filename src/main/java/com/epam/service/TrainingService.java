package com.epam.service;

import com.epam.model.Training;

import java.util.List;

public interface TrainingService {
    void createTraining(Training trainee);
    void updateTraining(Training trainee);
    void deleteTraining(Integer traineeId);
    Training getTrainingById(Integer traineeId);
    List<Training> getAllTrainings();
}
