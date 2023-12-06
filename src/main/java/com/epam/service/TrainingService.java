package com.epam.service;

import com.epam.model.Training;

import java.util.List;
import java.util.Optional;

public interface TrainingService {

 Training createTraining(Training training);
 Optional<Training> updateTraining(Training training);
 Optional<Training> getTrainingById(Integer trainingId);
 List<Training> getAllTrainings();
 boolean deleteTraining(Integer trainingId);
}
