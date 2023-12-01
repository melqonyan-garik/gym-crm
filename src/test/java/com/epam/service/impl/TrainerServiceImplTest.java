package com.epam.service.impl;

import com.epam.dao.TrainerDao;
import com.epam.dto.TrainerJsonDto;
import com.epam.mappers.Mappers;
import com.epam.model.Trainer;
import com.epam.model.User;
import com.epam.utils.UserUtils;
import mock.TrainerMockData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class TrainerServiceImplTest {

    @Mock
    private TrainerDao trainerDAO;
    @Mock
    private UserUtils userUtils;
    @InjectMocks
    private TrainerServiceImpl trainerService;

    @Test
    void testFindById() {
        TrainerJsonDto mockDataTrainer = TrainerMockData.getMockedTrainer_2().get(0);
        Trainer mockTrainer = Mappers.convertTrainerJsonDtoToTrainer(mockDataTrainer);
        when(trainerDAO.findByUsername(mockTrainer.getUser().getUsername())).thenReturn(mockTrainer);
        when(trainerDAO.findById(mockTrainer.getId())).thenReturn(mockTrainer);
        // When
        Trainer result = trainerService.getTrainerById(mockTrainer.getId());
        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockTrainer, result);
        Assertions.assertEquals(result.getId(), mockTrainer.getId());


    }

    @Test
    void testCheckUsernameAndPasswordMatching() {
        String username = "testUser";
        String password = "testPassword";
        Trainer mockTrainee = new Trainer();
        User mockUser = new User();

        mockUser.setPassword(password);
        mockTrainee.setUser(mockUser);
        when(trainerDAO.findByUsername(username)).thenReturn(mockTrainee);

        assertDoesNotThrow(() -> trainerService.checkUsernameAndPasswordMatching(username, password));
        verify(trainerDAO).findByUsername(username);
    }

    @Test
    void testCheckUsernameAndPasswordMatching_InvalidPassword() {
        String username = "testUser";
        String password = "testPassword";
        Trainer mockTrainee = new Trainer();
        User mockUser = new User();
        mockUser.setPassword("incorrectPassword");
        mockTrainee.setUser(mockUser);
        when(trainerDAO.findByUsername(username)).thenReturn(mockTrainee);

        assertThrows(RuntimeException.class, () -> trainerService.checkUsernameAndPasswordMatching(username, password));
        verify(trainerDAO).findByUsername(username);
    }
    @Test
    public void testCreateTrainers() {
        Trainer mockedTrainer1 = TrainerMockData.getMockedTrainer_1();

        Trainer mockedTrainer2 = Mappers.convertTrainerJsonDtoToTrainer(TrainerMockData.getMockedTrainer_2().get(0));

        trainerService.createTrainer(mockedTrainer1);
        trainerService.createTrainer(mockedTrainer2);

        verify(trainerDAO).save(mockedTrainer1);
        verify(trainerDAO).save(mockedTrainer2);
    }
    @Test
    void testDeleteTrainer() {
        Integer traineeIdToDelete = 1;
        Trainer mockedTrainee = TrainerMockData.getMockedTrainer_1();
        when(trainerDAO.findById(traineeIdToDelete)).thenReturn(mockedTrainee);
        when(trainerDAO.findByUsername(mockedTrainee.getUser().getUsername())).thenReturn(mockedTrainee);
        trainerService.deleteTrainer(traineeIdToDelete);
        verify(trainerDAO).delete(traineeIdToDelete);

    }
    @Test
    void testUpdateTrainer() {
        TrainerJsonDto mockedTrainerJson = TrainerMockData.getMockedTrainer_2().get(1);
        Trainer mockedTrainer = Mappers.convertTrainerJsonDtoToTrainer(mockedTrainerJson);
        when(trainerDAO.findByUsername(mockedTrainer.getUser().getUsername())).thenReturn(mockedTrainer);

        trainerService.updateTrainer(mockedTrainer);
        verify(trainerDAO).update(mockedTrainer.getId(),mockedTrainer);
    }
    @Test
    void testActivateDeactivateTrainer() {
        Trainer mockedTrainer = TrainerMockData.getMockedTrainer_1();
        when(trainerDAO.findByUsername(mockedTrainer.getUser().getUsername())).thenReturn(mockedTrainer);
        when(trainerDAO.findById(mockedTrainer.getId())).thenReturn(mockedTrainer);
        trainerService.activateDeactivateTrainer(mockedTrainer.getId(), mockedTrainer.getUser().isActive());

        verify(trainerDAO).save(mockedTrainer);
    }
    @Test
    void testChangePassword() {
        Trainer mockedTrainer = TrainerMockData.getMockedTrainer_1();
        when(trainerDAO.findById(mockedTrainer.getId())).thenReturn(mockedTrainer);
        when(trainerDAO.findByUsername(mockedTrainer.getUser().getUsername())).thenReturn(mockedTrainer);
        trainerService.changePassword(mockedTrainer.getId(),mockedTrainer.getUser().getPassword()
                ,"newPass");
        verify(trainerDAO).save(mockedTrainer);
    }

    @Test
    void testGetAllTrainers() {
        Trainer trainer1 = new Trainer();
        Trainer trainer2 = new Trainer();
        List<Trainer> allTrainees = List.of(trainer1, trainer2);
        when(trainerDAO.findAll()).thenReturn(allTrainees);

        List<Trainer> result = trainerService.getAllTrainer();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }
}
