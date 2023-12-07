package com.epam.dao;

import com.epam.dto.TrainerJsonDto;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainerDaoTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private TrainerDao trainerDao;

    @Test
    void testSave() {
        Trainer trainer = new Trainer();
        trainerDao.save(trainer);
        verify(entityManager).persist(trainer);
    }

    @Test
    void testGetTrainingsByUsername() {
        String username = "testUser";

        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<Trainer> cq = mock(CriteriaQuery.class);
        Root<Trainer> trainerRoot = mock(Root.class);
        Join<Object, Object> userJoin = mock(Join.class);
        TypedQuery<Trainer> query = mock(TypedQuery.class);
        Predicate predicate = mock(Predicate.class);

        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Trainer.class)).thenReturn(cq);
        when(cq.from(Trainer.class)).thenReturn(trainerRoot);
        when(cq.select(trainerRoot)).thenReturn(cq);
        when(trainerRoot.get("user")).thenReturn(userJoin);
        when(cb.like(userJoin.get("username"), username)).thenReturn(predicate);
        when(entityManager.createQuery(cq)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new Trainer());

        List<Training> result = trainerDao.getTrainingsByUsername(username);
        Assertions.assertNull(result);
        verify(entityManager).getCriteriaBuilder();
        verify(cb).createQuery(Trainer.class);
        verify(cq).from(Trainer.class);
        verify(cq).select(trainerRoot);
        verify(cb).like(userJoin.get("username"), username);
        verify(entityManager).createQuery(cq);
        verify(query).getSingleResult();
    }

    @Test
    void testFindAll() {
        List<Trainer> expectedTrainers = List.of(new Trainer(), new Trainer());
        Query query = mock(Query.class);

        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedTrainers);
        List<Trainer> result = trainerDao.findAll();

        assertNotNull(result);
        assertEquals(expectedTrainers, result);

        verify(entityManager).createQuery(anyString());
        verify(query).getResultList();
    }

    @Test
    void testDeleteById() {
        Integer trainerId = 1;
        when(entityManager.find(Trainer.class, trainerId)).thenReturn(new Trainer());
        trainerDao.delete(trainerId);

        verify(entityManager).remove(any(Trainer.class));
        verify(entityManager).find(Trainer.class, trainerId);
    }

    @Test
    void testFindByUsername() {
        String username = "testUser";

        TypedQuery<Trainer> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Trainer.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new Trainer());

        Trainer result = trainerDao.findByUsername(username).get();

        assertNotNull(result);
        verify(entityManager).createQuery(anyString(), eq(Trainer.class));
        verify(query).setParameter(anyString(), any());
        verify(query).getSingleResult();
    }

    @Test
    void testUpdate() {

        Trainer existingTrainer = TrainerMockData.getMockedTrainer_1();

        TrainerJsonDto trainerJsonDto = TrainerMockData.getMockedTrainer_2().get(0);
        Trainer updatedTrainer = Mappers.convertTrainerJsonDtoToTrainer(trainerJsonDto);
        Trainee mockedTrainee1 = TraineeMockData.getMockedTrainee_1();

        List<Trainee> trainees = new ArrayList<>();
        trainees.add(mockedTrainee1);
        updatedTrainer.setTrainees(trainees);

        when(entityManager.merge(updatedTrainer)).thenReturn(updatedTrainer);

        Optional<Trainer> optionalTrainer = trainerDao.update(updatedTrainer);
        Trainer trainer = optionalTrainer.get();

        Assertions.assertTrue(optionalTrainer.isPresent());
        Assertions.assertEquals(updatedTrainer.getUser().getFirstName(), trainer.getUser().getFirstName());
        Assertions.assertEquals(updatedTrainer.getUser().isActive(), trainer.getUser().isActive());

        verify(entityManager, times(1)).merge(any());
    }
}

