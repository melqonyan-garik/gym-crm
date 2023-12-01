package com.epam.service;

import com.epam.model.Training;

import java.util.List;

public interface TrainingService {

 void createTraining(Training training);
 void updateTraining(Training training);
 void deleteTraining(Integer trainingId);
 Training getTrainingById(Integer trainingId);
 List<Training> getAllTrainings();
}
