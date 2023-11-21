package com.epam.facade;

import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.service.TraineeService;
import com.epam.service.TrainerService;
import com.epam.service.TrainingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class Facade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    @Autowired
    public Facade(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    public List<Trainee> getAllTrainees() {
        try {
            log.info("Fetching all trainees.");
            return traineeService.getAllTrainees();
        } catch (Exception e) {
            log.error("Error fetching all trainees.", e);
            throw e;
        }
    }

    public List<Trainer> getAllTrainer() {
        try {
            log.info("Fetching all trainers.");
            return trainerService.getAllTrainer();
        } catch (Exception e) {
            log.error("Error fetching all trainers.", e);
            throw e;
        }

    }

    public List<Training> getAllTrainings() {
        try {
            log.info("Fetching all trainings.");
            return trainingService.getAllTrainings();
        } catch (Exception e) {
            log.error("Error fetching all trainings.", e);
            throw e;
        }

    }
}
