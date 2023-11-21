package com.epam.service.impl;

import com.epam.config.AppConfig;
import com.epam.model.Trainer;
import com.epam.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class TrainerServiceImplTest {

    private TrainerServiceImpl trainerService;

    @BeforeEach
    public void setUp() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        this.trainerService = context.getBean(TrainerServiceImpl.class);

    }

    @Test
    void  generateTrainerUsername_test() {
        Trainer trainer = new Trainer();
        User user = new User();
        user.setFirstName("john");
        user.setLastName("smith");
        trainer.setUser(user);
        String username = trainerService.generateTrainerUsername(trainer);
        Assertions.assertEquals("john.smith", username);
    }

    @Test
    void generateTrainerUsername_serial_test() {
        // Create a mock Trainer and its dependencies
        Trainer trainer = new Trainer();
        User user = new User();
        user.setFirstName("john");
        user.setLastName("smith");
        trainer.setUser(user);
        trainerService.createTrainer(trainer);

        String username = trainerService.generateTrainerUsername(trainer);
        Assertions.assertEquals("john.smith1", username);
    }

    @Test
    public void testCreateProfile_test() {
        Trainer trainer = new Trainer();
        User user = new User();
        user.setFirstName("Alice");
        user.setLastName("Johnson");
        trainer.setUser(user);
        List<Trainer> existingTrainers = new ArrayList<>();

        trainerService.createTrainer(trainer);

        Assertions.assertEquals("Alice.Johnson", user.getUsername());
        Assertions.assertFalse(usernameExists(user.getUsername(), existingTrainers));
    }
    @Test
    public void testDeleteTrainer() {
        Trainer trainer = new Trainer();
        trainer.setId(1);
        trainer.setUser(new User());
        trainerService.createTrainer(trainer);
        trainerService.deleteTrainer(1);

        Assertions.assertNull(trainerService.getTrainerById(1));
    }

    @Test
    public void testUpdateTrainer() {
        Trainer trainer = new Trainer();
        trainer.setUser(new User());
        trainerService.createTrainer(trainer);
        Trainer createdTrainer = trainerService.getAllTrainer().get(0);
        User user = new User();
        user.setFirstName("John");
        createdTrainer.setUser(user);
        trainerService.updateTrainer(createdTrainer);

        Assertions.assertEquals(createdTrainer.getUser().getFirstName(), trainer.getUser().getFirstName());
    }

    @Test
    public void testGetTrainerById() {
        Trainer trainer = new Trainer();
        trainer.setId(1);
        trainer.setUser(new User());

        trainerService.createTrainer(trainer);
        Trainer createdTrainer = trainerService.getAllTrainer().get(0);
        Trainer retrievedTrainer = trainerService.getTrainerById(trainer.getId());

        Assertions.assertEquals(createdTrainer, retrievedTrainer);
    }

    @Test
    public void testGetAllTrainer() {
        Trainer trainer1 = new Trainer();
        trainer1.setId(1);
        trainer1.setUser(new User());

        Trainer trainer2 = new Trainer();
        trainer2.setId(2);
        trainer2.setUser(new User());

        trainerService.createTrainer(trainer1);
        trainerService.createTrainer(trainer2);

        List<Trainer> allTrainers = trainerService.getAllTrainer();

        Assertions.assertEquals(2, allTrainers.size());
        Assertions.assertTrue(allTrainers.contains(trainer1));
        Assertions.assertTrue(allTrainers.contains(trainer2));
    }


    private boolean usernameExists(String username, List<Trainer> trainers) {
        for (Trainer existingTrainer : trainers) {
            if (existingTrainer.getUser().getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
