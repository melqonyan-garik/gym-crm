package com.epam.facade;


import com.epam.dto.TraineeJsonDto;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.service.TraineeService;
import com.epam.service.TrainerService;
import com.epam.service.TrainingService;
import mock.TraineeMockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FacadeTest {

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private Facade facade;

    @Test
    void testGetAllTrainees() {
        List<Trainee> expectedTrainees = List.of(new Trainee(), new Trainee());
        when(traineeService.getAllTrainees()).thenReturn(expectedTrainees);

        List<Trainee> result = facade.getAllTrainees();

        assertNotNull(result);
        assertEquals(expectedTrainees, result);

    }

    @Test
    void testGetAllTrainer() {
        List<Trainer> expectedTrainers = List.of(new Trainer(), new Trainer());
        when(trainerService.getAllTrainer()).thenReturn(expectedTrainers);

        List<Trainer> result = facade.getAllTrainer();

        assertNotNull(result);
        assertEquals(expectedTrainers, result);

    }
    @Test
    void testGetAllTrainings() {
        List<Training> expectedTrainings = List.of(new Training(), new Training());
        when(trainingService.getAllTrainings()).thenReturn(expectedTrainings);

        List<Training> result = facade.getAllTrainings();

        assertNotNull(result);
        assertEquals(expectedTrainings, result);
    }

    @Test
    void testSaveTraineeFromTraineeJsonData() {
        TraineeJsonDto traineeJsonDto = TraineeMockData.getMockedTrainee_2().get(0);
        facade.saveTraineeFromTraineeJsonData(traineeJsonDto);
        verify(traineeService).createTrainee(any(Trainee.class));
    }
}
