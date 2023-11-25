package com.epam.service.impl;

import com.epam.config.AppConfig;
import com.epam.model.Trainee;
import com.epam.model.User;
import com.epam.utils.PasswordGeneratorUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TraineeServiceImplTest {
    private TraineeServiceImpl traineeService;
/*
    @BeforeEach
    public void setUp() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        this.traineeService = context.getBean(TraineeServiceImpl.class);

    }

    @Test
    void testGenerateTraineeUsername() {
        Trainee trainee = new Trainee();
        User user = new User();
        user.setFirstName("john");
        user.setLastName("smith");
        trainee.setUser(user);
        String username = traineeService.generateTraineeUsername(trainee);
        Assertions.assertEquals("john.smith", username);
    }

    @Test
    void testGenerateTraineeUsername_serial() {
        Trainee trainee = new Trainee();
        User user = new User();
        user.setFirstName("john");
        user.setLastName("smith");
        trainee.setUser(user);
        traineeService.createTrainee(trainee);

        String username = traineeService.generateTraineeUsername(trainee);
        Assertions.assertEquals("john.smith1", username);
    }

    @Test
    public void testCreateProfile() {
        Trainee trainee = new Trainee();
        trainee.setId(1);
        trainee.setAddress("address");
        trainee.setDateOfBirth(LocalDate.of(2000, 12, 12));
        trainee.setTrainers(List.of());
        trainee.setTrainings(List.of());
        User user = new User();
        user.setId(1);
        user.setFirstName("Alice");
        user.setLastName("Johnson");
        user.setPassword("some password");
        user.setUsername("alice");
        user.setActive(true);
        trainee.setUser(user);
        List<Trainee> existingTrainees = new ArrayList<>();

        traineeService.createTrainee(trainee);

        Assertions.assertEquals("Alice.Johnson", user.getUsername());
        Assertions.assertFalse(usernameExists(user.getUsername(), existingTrainees));
        Trainee createdTrainee = traineeService.getAllTrainees().get(0);
        Assertions.assertEquals(createdTrainee.getId(), trainee.getId());
        Assertions.assertEquals(createdTrainee.getAddress(), trainee.getAddress());
        Assertions.assertEquals(createdTrainee.getDateOfBirth(), trainee.getDateOfBirth());
        Assertions.assertEquals(createdTrainee.getTrainings(), trainee.getTrainings());
        Assertions.assertEquals(createdTrainee.getTrainers(), trainee.getTrainers());
        Assertions.assertEquals(createdTrainee.getUser().getId(), trainee.getUser().getId());
        Assertions.assertEquals(createdTrainee.getUser().getFirstName(), trainee.getUser().getFirstName());
        Assertions.assertEquals(createdTrainee.getUser().getLastName(), trainee.getUser().getLastName());
        Assertions.assertEquals(createdTrainee.getUser().getPassword(), trainee.getUser().getPassword());
        Assertions.assertEquals(createdTrainee.getUser().isActive(), trainee.getUser().isActive());
    }

    @Test
    public void testDeleteTrainee() {
        Trainee trainee = new Trainee();
        trainee.setId(1);
        trainee.setAddress("Address");
        traineeService.createTrainee(trainee);
        traineeService.deleteTrainee(1);

        Assertions.assertNull(traineeService.getTraineeById(1));
    }

    @Test
    public void testUpdateTrainee() {
        Trainee trainee = new Trainee();
        trainee.setAddress("Address");
        traineeService.createTrainee(trainee);
        Trainee createdTrainee = traineeService.getAllTrainees().get(0);
        createdTrainee.setAddress("Updated Address");
        traineeService.updateTrainee(createdTrainee);

        Assertions.assertEquals(createdTrainee.getAddress(), trainee.getAddress());
    }

    @Test
    public void testGetTraineeById() {
        Trainee trainee = new Trainee();
        trainee.setId(1);
        trainee.setDateOfBirth(LocalDate.now());

        traineeService.createTrainee(trainee);
        Trainee createdTrainee = traineeService.getAllTrainees().get(0);
        Trainee retrievedTrainee = traineeService.getTraineeById(trainee.getId());

        Assertions.assertEquals(createdTrainee, retrievedTrainee);
    }

    @Test
    public void testGetAllTrainee() {
        Trainee trainee1 = new Trainee();
        trainee1.setId(1);
        trainee1.setAddress("Address1");

        Trainee trainee2 = new Trainee();
        trainee2.setId(2);
        trainee2.setAddress("Address2");

        traineeService.createTrainee(trainee1);
        traineeService.createTrainee(trainee2);

        List<Trainee> allTrainees = traineeService.getAllTrainees();

        Assertions.assertEquals(2, allTrainees.size());
        Assertions.assertTrue(allTrainees.contains(trainee1));
        Assertions.assertTrue(allTrainees.contains(trainee2));
    }

    @Test
    public void testGenerateRandomPassword() {
        String password = PasswordGeneratorUtils.generateRandomPassword();
        Assertions.assertEquals(10, password.length());
    }

    private boolean usernameExists(String username, List<Trainee> trainees) {
        for (Trainee existingTrainee : trainees) {
            if (existingTrainee.getUser().getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

 */
}
