package com.epam.service.impl;

import com.epam.config.AppConfig;
import com.epam.model.Training;
import com.epam.model.TrainingType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class TrainingServiceImplTest {
    private TrainingServiceImpl trainingService;
/*
    @BeforeEach
    public void setUp() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        this.trainingService = context.getBean(TrainingServiceImpl.class);

    }

    @Test
    public void testCreateTraining() {
        // Create a Training instance
        Training training = new Training();
        training.setId(1);
        training.setTrainingName("Java Basics");
        training.setTrainingDate(LocalDate.now());
        training.setTrainingDuration(2);
        TrainingType trainingType = new TrainingType();
        trainingType.setId(1);
        trainingType.setTrainingTypeName("Java Basics");
        trainingType.setTrainings(List.of());
        trainingType.setTrainers(List.of());
        training.setTrainingType(trainingType);


        trainingService.createTraining(training);

        List<Training> allTrainings = trainingService.getAllTrainings();
        Assertions.assertEquals(allTrainings.size(), 1);
        Training createdTraining = allTrainings.get(0);
        Assertions.assertEquals(createdTraining.getId(), training.getId());
        Assertions.assertEquals(createdTraining.getTrainingName(), training.getTrainingName());
        Assertions.assertEquals(createdTraining.getTrainingDate(), training.getTrainingDate());
        Assertions.assertEquals(createdTraining.getTrainingDuration(), training.getTrainingDuration());
        Assertions.assertEquals(createdTraining.getTrainingType().getTrainingTypeName(), training.getTrainingType().getTrainingTypeName());
        Assertions.assertEquals(createdTraining.getTrainingType().getId(), training.getTrainingType().getId());
        Assertions.assertEquals(createdTraining.getTrainingType().getTrainings(), Collections.emptyList());
        Assertions.assertEquals(createdTraining.getTrainingType().getTrainers(), Collections.emptyList());


    }

    @Test
    public void testDeleteTraining() {
        Training training = new Training();
        training.setId(1);
        training.setTrainingName("Java Advanced");
        trainingService.createTraining(training);
        trainingService.deleteTraining(1);

        Assertions.assertNull(trainingService.getTrainingById(1));
    }

    @Test
    public void testUpdateTraining() {
        Training training = new Training();
        training.setTrainingName("Database Fundamentals");
        trainingService.createTraining(training);
        Training createdTraining = trainingService.getAllTrainings().get(0);
        createdTraining.setTrainingName("Database Advanced");
        trainingService.updateTraining(createdTraining);

        Assertions.assertEquals(createdTraining.getTrainingName(), training.getTrainingName());
    }

    @Test
    public void testGetTrainingById() {
        Training training = new Training();
        training.setId(1);
        training.setTrainingName("Python Basics");

        trainingService.createTraining(training);
        Training createdTraining = trainingService.getAllTrainings().get(0);


        Training retrievedTraining = trainingService.getTrainingById(createdTraining.getId());


        Assertions.assertEquals(createdTraining, retrievedTraining);
    }

    @Test
    public void testGetAllTrainings() {
        Training training1 = new Training();
        training1.setId(1);
        training1.setTrainingName("Machine Learning");

        Training training2 = new Training();
        training2.setId(2);
        training2.setTrainingName("Deep Learning");

        trainingService.createTraining(training1);
        trainingService.createTraining(training2);

        List<Training> allTrainings = trainingService.getAllTrainings();

        Assertions.assertEquals(2, allTrainings.size());
        Assertions.assertTrue(allTrainings.contains(training1));
        Assertions.assertTrue(allTrainings.contains(training2));
    }
*/
}
