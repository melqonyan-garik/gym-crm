package com.epam.dao;

import com.epam.dto.TraineeJsonDto;
import com.epam.mappers.Mappers;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import mock.TraineeMockData;
import mock.TrainerMockData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TraineeDaoTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private TraineeDao traineeDAO;

    @Test
    void testSave() {
        Trainee trainee = new Trainee();
        traineeDAO.save(trainee);
        verify(entityManager).persist(trainee);
    }

    @Test
    void testGetTrainingsByUsername() {
        String username = "testUser";

        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<Trainee> cq = mock(CriteriaQuery.class);
        Root<Trainee> traineeRoot = mock(Root.class);
        Join<Object, Object> userJoin = mock(Join.class);
        TypedQuery<Trainee> query = mock(TypedQuery.class);
        Predicate predicate = mock(Predicate.class);

        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Trainee.class)).thenReturn(cq);
        when(cq.from(Trainee.class)).thenReturn(traineeRoot);
        when(cq.select(traineeRoot)).thenReturn(cq);
        when(traineeRoot.get("user")).thenReturn(userJoin);
        when(cb.like(userJoin.get("username"), username)).thenReturn(predicate);
        when(entityManager.createQuery(cq)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new Trainee());

        List<Training> result = traineeDAO.getTrainingsByUsername(username);
        Assertions.assertNull(result);
        verify(entityManager).getCriteriaBuilder();
        verify(cb).createQuery(Trainee.class);
        verify(cq).from(Trainee.class);
        verify(cq).select(traineeRoot);
        verify(cb).like(userJoin.get("username"), username);
        verify(entityManager).createQuery(cq);
        verify(query).getSingleResult();
    }

    @Test
    void testFindAll() {
        List<Trainee> expectedTrainees = List.of(new Trainee(), new Trainee());
        Query query = mock(Query.class);

        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedTrainees);
        List<Trainee> result = traineeDAO.findAll();

        assertNotNull(result);
        assertEquals(expectedTrainees, result);

        verify(entityManager).createQuery(anyString());
        verify(query).getResultList();
    }

    @Test
    void testDeleteById() {
        Integer traineeId = 1;
        when(entityManager.find(Trainee.class, traineeId)).thenReturn(new Trainee());
        traineeDAO.delete(traineeId);

        verify(entityManager).remove(any(Trainee.class));
        verify(entityManager).find(Trainee.class, traineeId);
    }

    @Test
    void testDeleteByUsername() {
        String username = "testUser";
        TypedQuery<Trainee> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Trainee.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new Trainee());

        traineeDAO.deleteByUsername(username);

        verify(entityManager).remove(any(Trainee.class));
        verify(entityManager).createQuery(anyString(), eq(Trainee.class));
        verify(query).setParameter(anyString(), any());
        verify(query).getSingleResult();
    }

    @Test
    void testFindByUsername() {
        String username = "testUser";

        TypedQuery<Trainee> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Trainee.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new Trainee());

        // When
        Trainee result = traineeDAO.findByUsername(username);

        // Then
        assertNotNull(result);

        // Optionally, you can add more assertions based on the expected behavior
        verify(entityManager).createQuery(anyString(), eq(Trainee.class));
        verify(query).setParameter(anyString(), any());
        verify(query).getSingleResult();
    }

    @Test
    void testUpdate() {
        Trainee existingTrainee = TraineeMockData.getMockedTrainee_1();

        TraineeJsonDto traineeJsonDto = TraineeMockData.getMockedTrainee_2().get(0);
        Trainee updatedTrainee = Mappers.convertTraineeJsonDtoToTrainee(traineeJsonDto);
        Trainer mockedTrainer1 = TrainerMockData.getMockedTrainer_1();
        List<Trainer> trainers = new ArrayList<>();
        trainers.add(mockedTrainer1);
        updatedTrainee.setTrainers(trainers);

        when(entityManager.find(Trainee.class, existingTrainee.getId())).thenReturn(existingTrainee);
        boolean result = traineeDAO.update(existingTrainee.getId(), updatedTrainee);

        Assertions.assertTrue(result);
        Assertions.assertEquals(updatedTrainee.getAddress(), existingTrainee.getAddress());
        Assertions.assertEquals(updatedTrainee.getDateOfBirth(), existingTrainee.getDateOfBirth());

        verify(entityManager, times(2)).find(any(),any());
    }
}

