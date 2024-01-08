package com.epam.controller;

import com.epam.dto.training.TrainingRequest;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.User;
import com.epam.service.TraineeService;
import com.epam.service.TrainerService;
import com.epam.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TrainingControllerTest {

    @Mock
    private TrainingService trainingService;

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private TrainingController trainingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTraining_Successful() {
        TrainingRequest trainingRequest = new TrainingRequest();
        trainingRequest.setTrainingName("Java Basics");
        trainingRequest.setTrainingDate(LocalDate.now().toString());
        trainingRequest.setTrainingDuration("5");
        trainingRequest.setTraineeUsername("traineeUser");
        trainingRequest.setTrainerUsername("trainerUser");

        Trainee trainee = new Trainee();
        User userTrainee = new User();
        userTrainee.setUsername("traineeUser");
        trainee.setUser(userTrainee);
        Trainer trainer = new Trainer();
        User userTrainer = new User();
        userTrainer.setUsername("trainerUser");
        trainer.setUser(userTrainer);
        when(traineeService.getTraineeByUsername("traineeUser")).thenReturn(Optional.of(trainee));
        when(trainerService.getTrainerByUsername("trainerUser")).thenReturn(Optional.of(trainer));

        ResponseEntity<String> responseEntity = trainingController.addTraining(trainingRequest);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("Training added successfully. Status: 200 OK", responseEntity.getBody());
    }

    @Test
    void testAddTraining_UnsuccessfulTraineeNotFound() {
        TrainingRequest trainingRequest = new TrainingRequest();
        trainingRequest.setTrainingName("Java Basics");
        trainingRequest.setTrainingDate(LocalDate.now().toString());
        trainingRequest.setTrainingDuration("5");
        trainingRequest.setTraineeUsername("nonExistentTrainee");
        trainingRequest.setTrainerUsername("trainerUser");

        when(traineeService.getTraineeByUsername("nonExistentTrainee"))
                .thenReturn(Optional.empty());
        when(trainerService.getTrainerByUsername("trainerUser"))
                .thenReturn(Optional.of(new Trainer()));

        ResponseEntity<String> responseEntity = trainingController.addTraining(trainingRequest);
        assertEquals(500, responseEntity.getStatusCodeValue()); // You may want to change this status code based on your error handling
        assertEquals("Trainee not found. Status: 500 Internal Server Error", responseEntity.getBody());
    }

    @Test
    void testAddTraining_UnsuccessfulTrainerNotFound() {
        TrainingRequest trainingRequest = new TrainingRequest();
        trainingRequest.setTrainingName("Java Basics");
        trainingRequest.setTrainingDate(LocalDate.now().toString());
        trainingRequest.setTrainingDuration("5");
        trainingRequest.setTrainerUsername("nonExistentTrainer");
        trainingRequest.setTraineeUsername("traineeUser");

        when(traineeService.getTraineeByUsername("traineeUser")).thenReturn(Optional.of(new Trainee()));
        when(trainerService.getTrainerByUsername("nonExistentTrainer")).thenReturn(Optional.empty());

        ResponseEntity<String> responseEntity = trainingController.addTraining(trainingRequest);

        assertEquals(500, responseEntity.getStatusCodeValue());
        assertEquals("Trainer not found. Status: 500 Internal Server Error", responseEntity.getBody());
    }
}
