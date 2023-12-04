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
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    void testAreUsernameAndPasswordMatching_ValidUsernameAndPassword() {
        String username = "testUser";
        String password = "testPassword";
        Trainer mockTrainer = new Trainer();
        User mockUser = new User();

        mockUser.setPassword(password);
        mockTrainer.setUser(mockUser);
        when(trainerDAO.findByUsername(username)).thenReturn(mockTrainer);

        assertDoesNotThrow(() -> trainerService.areUsernameAndPasswordMatching(username, password));
        verify(trainerDAO).findByUsername(username);
    }

    @Test
    void testAreUsernameAndPasswordMatching_InvalidPassword() {
        String username = "testUser";
        String password = "testPassword";
        Trainer mockTrainer = new Trainer();
        User mockUser = new User();
        mockUser.setPassword("incorrectPassword");
        mockTrainer.setUser(mockUser);
        when(trainerDAO.findByUsername(username)).thenReturn(mockTrainer);

        boolean areUsernameAndPasswordMatching = trainerService.areUsernameAndPasswordMatching(username, password);
        assertFalse(areUsernameAndPasswordMatching);
        verify(trainerDAO).findByUsername(username);
    }
    @Test
    void testAreUsernameAndPasswordMatching_NullUsername() {
        String username = null;
        String password = "testPassword";

        assertThrows(IllegalArgumentException.class,()-> trainerService.areUsernameAndPasswordMatching(username, password));
    }

    @Test
    void testAreUsernameAndPasswordMatching_NullPassword() {
        String username = "testUser";
        String password = null;

        assertThrows(IllegalArgumentException.class,()-> trainerService.areUsernameAndPasswordMatching(username, password));
    }

    @Test
    void testAreUsernameAndPasswordMatching_NullUsernameAndPassword() {
        String username = null;
        String password = null;

        assertThrows(IllegalArgumentException.class,()-> trainerService.areUsernameAndPasswordMatching(username, password));
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
        Integer trainerIdToDelete = 1;
        Trainer mockedTrainer = TrainerMockData.getMockedTrainer_1();
        when(trainerDAO.findById(trainerIdToDelete)).thenReturn(mockedTrainer);
        when(trainerDAO.findByUsername(mockedTrainer.getUser().getUsername())).thenReturn(mockedTrainer);
        trainerService.deleteTrainer(trainerIdToDelete);
        verify(trainerDAO).delete(trainerIdToDelete);

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
        List<Trainer> allTrainers = List.of(trainer1, trainer2);
        when(trainerDAO.findAll()).thenReturn(allTrainers);

        List<Trainer> result = trainerService.getAllTrainer();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }
}
