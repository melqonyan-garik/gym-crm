package com.epam.controller;

import com.epam.dao.TrainingTypeDao;
import com.epam.dto.trainingType.TrainingTypeResponse;
import com.epam.model.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TrainingTypeControllerTest {

    @Mock
    private TrainingTypeDao trainingTypeDao;

    @InjectMocks
    private TrainingTypeController trainingTypeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTrainingTypes() {
        // Mocking data
        TrainingType trainingType1 = new TrainingType(1, "Type 1", List.of(), List.of());
        TrainingType trainingType2 = new TrainingType(2, "Type 2", List.of(), List.of());
        List<TrainingType> mockTrainingTypes = Arrays.asList(trainingType1, trainingType2);

        // Stubbing the trainingTypeDao
        when(trainingTypeDao.getAllTrainingTypes()).thenReturn(mockTrainingTypes);

        // Call the controller method
        ResponseEntity<List<TrainingTypeResponse>> responseEntity = trainingTypeController.getTrainingTypes();

        // Verify the response
        assertEquals(200, responseEntity.getStatusCodeValue());

        List<TrainingTypeResponse> trainingTypeResponses = responseEntity.getBody();
        assertEquals(2, trainingTypeResponses.size());
        assertEquals("Type 1", trainingTypeResponses.get(0).getTrainingType());
        assertEquals(1, trainingTypeResponses.get(0).getTrainingTypeId());
        assertEquals("Type 2", trainingTypeResponses.get(1).getTrainingType());
        assertEquals(2, trainingTypeResponses.get(1).getTrainingTypeId());
    }
}
