package com.epam.controller;

import com.epam.dto.training.TrainingRequest;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.service.TraineeService;
import com.epam.service.TrainerService;
import com.epam.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingService trainingService;
    private final TraineeService traineeService;
    private final TrainerService trainerService;

    @PostMapping("/training")
    public ResponseEntity<String> addTraining(@RequestBody @Valid TrainingRequest trainingRequest) {
        Training training = new Training();
        training.setTrainingName(trainingRequest.getTrainingName());
        training.setTrainingDate(LocalDate.parse(trainingRequest.getTrainingDate()));
        training.setTrainingDuration(Double.valueOf(trainingRequest.getTrainingDuration()));
        Optional<Trainee> traineeOptional = traineeService.getTraineeByUsername(trainingRequest.getTraineeUsername());
        if (traineeOptional.isEmpty()){
            return ResponseEntity.status(500).body("Trainee not found. Status: 500 Internal Server Error");
        }
        Optional<Trainer> trainerOptional = trainerService.getTrainerByUsername(trainingRequest.getTrainerUsername());
        if (trainerOptional.isEmpty()){
            return ResponseEntity.status(500).body("Trainer not found. Status: 500 Internal Server Error");

        }
        training.setTrainer(trainerOptional.get());
        training.setTrainee(traineeOptional.get());
        trainingService.createTraining(training);
        return ResponseEntity.ok("Training added successfully. Status: 200 OK");
    }
}
