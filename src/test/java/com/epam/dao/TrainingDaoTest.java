package com.epam.dao;

import com.epam.model.Training;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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
public class TrainingDaoTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private TrainingDao trainingDao;

    @Test
    void testSave() {
        Training training = new Training();
        trainingDao.save(training);
        verify(entityManager).persist(training);
    }

    @Test
    void testFindAll() {
        List<Training> expectedTrainings = List.of(new Training(), new Training());
        Query query = mock(Query.class);

        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedTrainings);
        List<Training> result = trainingDao.findAll();

        assertNotNull(result);
        assertEquals(expectedTrainings, result);

        verify(entityManager).createQuery(anyString());
        verify(query).getResultList();
    }

    @Test
    void testDeleteById() {
        Integer trainingId = 1;
        when(entityManager.find(Training.class, trainingId)).thenReturn(new Training());
        trainingDao.delete(trainingId);

        verify(entityManager).remove(any(Training.class));
        verify(entityManager).find(Training.class, trainingId);
    }



}

