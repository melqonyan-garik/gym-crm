package com.epam.controller;

import com.epam.dto.trainee.*;
import com.epam.dto.trainer.TrainerProfile;
import com.epam.mappers.TraineeMapper;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.User;
import com.epam.service.TraineeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TraineeControllerTest {

    @Mock
    private TraineeMapper mapper;

    @Mock
    private TraineeService traineeService;

    @InjectMocks
    private TraineeController traineeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Disabled
    void testRegisterTrainee_Successful() {
        // Mocking data
        TraineeRegistrationRequest request = new TraineeRegistrationRequest();

        Trainee trainee = new Trainee();
        User user = new User();
        user.setUsername("traineeUser");
        user.setPassword("password123");
        trainee.setUser(user);


        when(mapper.traineeRegistrationRequestToTrainee(request)).thenReturn(trainee);
        when(traineeService.createTrainee(trainee)).thenReturn(trainee);

        // Call the controller method
        ResponseEntity<TraineeRegistrationResponse> responseEntity = traineeController.registerTrainee(request);

        // Verify the response
        assertEquals(200, responseEntity.getStatusCodeValue());

        TraineeRegistrationResponse response = responseEntity.getBody();
        assertEquals("traineeUser", response.getUsername());
        assertEquals("password123", response.getPassword());
    }

    @Test
    void testGetTraineeProfile_Successful() {
        Trainee trainee = new Trainee();
        User user = new User();
        user.setFirstname("firstname");
        user.setLastname("lastname");
        String username = "traineeUser";
        user.setUsername(username);
        user.setPassword("password123");
        trainee.setUser(user);

        when(traineeService.getTraineeByUsername(username)).thenReturn(Optional.of(trainee));
        TraineeProfileResponse expectedProfile = new TraineeProfileResponse();
        when(mapper.traineeToTraineeProfileResponse(trainee)).thenReturn(expectedProfile);

        ResponseEntity<TraineeProfileResponse> responseEntity = traineeController.getTraineeProfile(username);
        assertEquals(200, responseEntity.getStatusCodeValue());

    }

    @Test
    @Disabled
    void testDeleteTraineeProfile_Successful() {
        String username = "traineeUser";

        when(traineeService.deleteTraineeByUsername(username)).thenReturn(true);
        ResponseEntity<String> responseEntity = traineeController.deleteTraineeProfile(username);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("Trainee profile deleted successfully. Status: 200 OK", responseEntity.getBody());
    }

    @Test
    void testGetNotAssignedTrainers_Successful() {
        // Mocking data
        String username = "traineeUser";

        Trainer trainer1 = new Trainer();
        User user1 = new User();
        user1.setUsername("trainer1");
        trainer1.setUser(user1);
        Trainer trainer2 = new Trainer();
        User user2 = new User();
        user2.setUsername("trainer2");
        trainer2.setUser(user2);
        List<Trainer> notAssignedTrainers = List.of(trainer1, trainer2);

        when(traineeService.getNotAssignedTrainers(username)).thenReturn(notAssignedTrainers);

        TrainerProfile trainerProfile1 = new TrainerProfile();
        trainerProfile1.setFirstname("firstName1");
        trainerProfile1.setUsername("username1");
        TrainerProfile trainerProfile2 = new TrainerProfile();
        trainerProfile2.setFirstname("firstName2");
        trainerProfile2.setUsername("username2");
        List<TrainerProfile> expectedTrainerProfiles = Arrays.asList(trainerProfile1, trainerProfile2);

        when(mapper.TrainersToTrainerProfiles(notAssignedTrainers)).thenReturn(expectedTrainerProfiles);

        // Call the controller method
        ResponseEntity<List<TrainerProfile>> responseEntity = traineeController.getNotAssignedTrainers(username);

        // Verify the response
        assertEquals(200, responseEntity.getStatusCodeValue());

        List<TrainerProfile> actualTrainerProfiles = responseEntity.getBody();
        assertEquals(expectedTrainerProfiles.size(), actualTrainerProfiles.size());
        assertEquals(expectedTrainerProfiles, actualTrainerProfiles);
    }

}
