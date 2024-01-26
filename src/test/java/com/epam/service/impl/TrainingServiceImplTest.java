package com.epam.service.impl;

import com.epam.dao.TrainingDao;
import com.epam.dto.json.TrainingJsonDto;
import com.epam.mappers.Mappers;
import com.epam.model.Training;
import mock.TrainingMockData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceImplTest {
    public static final String TRAINING_RESOURCE_NAME = "prepared-data-file-for-training.json";
    @InjectMocks
    private TrainingServiceImpl trainingService;
    @Mock
    private TrainingDao trainingDao;

    @Test
    public void testCreateTraining() {
        TrainingJsonDto mockedTrainingJsonData = TrainingMockData.getMockedTraining_2(TRAINING_RESOURCE_NAME);
        Training mockedTrainingData = Mappers.convertTrainingJsonDtoToTraining(mockedTrainingJsonData);

        trainingService.createTraining(mockedTrainingData);
        verify(trainingDao).save(mockedTrainingData);
    }

    @Test
    void testFindById() {
        TrainingJsonDto mockedTrainingJson = TrainingMockData.getMockedTraining_2(
                TRAINING_RESOURCE_NAME);
        Training mockedTraining = Mappers.convertTrainingJsonDtoToTraining(mockedTrainingJson);
        when(trainingDao.findById(mockedTraining.getId())).thenReturn(Optional.of(mockedTraining));
        Training result = trainingService.getTrainingById(mockedTraining.getId()).get();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockedTraining, result);
        Assertions.assertEquals(mockedTraining.getTrainingName(), result.getTrainingName());
        Assertions.assertEquals(mockedTraining.getTrainingDuration(), result.getTrainingDuration());
        Assertions.assertEquals(mockedTraining.getTrainingName(), result.getTrainingName());
        Assertions.assertEquals(mockedTraining.getId(), result.getId());
        Assertions.assertEquals(mockedTraining.getTrainee(), result.getTrainee());
        Assertions.assertEquals(mockedTraining.getTrainer(), result.getTrainer());
        Assertions.assertEquals(result.getId(), mockedTraining.getId());

    }

    @Test
    void testDeleteTraining() {
        Training mockedTraining = TrainingMockData.getMockedTraining_1();
        trainingService.deleteTraining(mockedTraining.getId());
        verify(trainingDao).delete(mockedTraining.getId());

    }

    @Test
    void testUpdateTraining() {
        Training mockedTraining = TrainingMockData.getMockedTraining_1();
        trainingService.updateTraining(mockedTraining);
        verify(trainingDao).update(mockedTraining);
    }

    @Test
    void testGetAllTraining() {
        Training training1 = TrainingMockData.getMockedTraining_1();
        Training training2 = Mappers.convertTrainingJsonDtoToTraining(TrainingMockData.getMockedTraining_2(TRAINING_RESOURCE_NAME));
        List<Training> allTrainings = List.of(training1, training2);
        when(trainingDao.findAll()).thenReturn(allTrainings);

        List<Training> result = trainingService.getAllTrainings();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }
}
