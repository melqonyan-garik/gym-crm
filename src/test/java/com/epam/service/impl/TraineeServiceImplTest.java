package com.epam.service.impl;

import com.epam.dao.TraineeDao;
import com.epam.dto.TraineeJsonDto;
import com.epam.mappers.Mappers;
import com.epam.model.Trainee;
import com.epam.model.User;
import com.epam.utils.UserUtils;
import mock.TraineeMockData;
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
public class TraineeServiceImplTest {

    @Mock
    private TraineeDao traineeDAO;
    @Mock
    private UserUtils userUtils;
    @InjectMocks
    private TraineeServiceImpl traineeService;

    @Test
    void testFindByUsername() {
        TraineeJsonDto traineeJsonDto = TraineeMockData.getMockedTrainee_2().get(0);
        Trainee mockedTrainee = Mappers.convertTraineeJsonDtoToTrainee(traineeJsonDto);
        when(traineeDAO.findByUsername(mockedTrainee.getUser().getUsername())).thenReturn(mockedTrainee);

        Trainee result = traineeService.getTraineeByUsername(mockedTrainee.getUser().getUsername());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockedTrainee, result);


        Assertions.assertEquals(result.getId(), mockedTrainee.getId());
        Assertions.assertEquals(result.getId(), traineeJsonDto.getId());
        Assertions.assertEquals(result.getAddress(), mockedTrainee.getAddress());
        Assertions.assertEquals(result.getDateOfBirth(), mockedTrainee.getDateOfBirth());
        Assertions.assertEquals(result.getTrainings(), mockedTrainee.getTrainings());
        Assertions.assertEquals(result.getTrainers(), mockedTrainee.getTrainers());
        Assertions.assertEquals(result.getUser().getId(), mockedTrainee.getUser().getId());
        Assertions.assertEquals(result.getUser().getId(), traineeJsonDto.getUser().getId());
        Assertions.assertEquals(result.getUser().getFirstName(), mockedTrainee.getUser().getFirstName());
        Assertions.assertEquals(result.getUser().getLastName(), mockedTrainee.getUser().getLastName());
        Assertions.assertEquals(result.getUser().getPassword(), mockedTrainee.getUser().getPassword());
        Assertions.assertEquals(result.getUser().isActive(), mockedTrainee.getUser().isActive());

    }

    @Test
    void testCheckUsernameAndPasswordMatching() {
        // Given
        String username = "testUser";
        String password = "testPassword";
        Trainee mockTrainee = new Trainee();
        User mockUser = new User();
        mockUser.setPassword(password);
        mockTrainee.setUser(mockUser);
        when(traineeDAO.findByUsername(username)).thenReturn(mockTrainee);

        // When/Then
        assertDoesNotThrow(() -> traineeService.checkUsernameAndPasswordMatching(username, password));

        // Optionally, verify that the DAO method was called with the correct parameter
        verify(traineeDAO).findByUsername(username);
    }
    @Test
    void testCheckUsernameAndPasswordMatching_InvalidPassword() {
        // Given
        String username = "testUser";
        String password = "testPassword";
        Trainee mockTrainee = new Trainee();
        User mockUser = new User();
        mockUser.setPassword("incorrectPassword");
        mockTrainee.setUser(mockUser);
        when(traineeDAO.findByUsername(username)).thenReturn(mockTrainee);

        // When/Then
        assertThrows(RuntimeException.class, () -> traineeService.checkUsernameAndPasswordMatching(username, password));

        // Optionally, verify that the DAO method was called with the correct parameter
        verify(traineeDAO).findByUsername(username);
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

        verify(traineeDAO).save(trainee1);
        verify(traineeDAO).save(trainee2);
    }
    @Test
    void testDeleteTrainee() {
        Integer traineeIdToDelete = 1;
        Trainee mockedTrainee = TraineeMockData.getMockedTrainee_1();
        when(traineeDAO.findById(traineeIdToDelete)).thenReturn(mockedTrainee);
        when(traineeDAO.findByUsername(mockedTrainee.getUser().getUsername())).thenReturn(mockedTrainee);
        traineeService.deleteTrainee(traineeIdToDelete);
        verify(traineeDAO).delete(traineeIdToDelete);

    }
    @Test
    void testUpdateTrainee() {
        Trainee mockedTrainee = TraineeMockData.getMockedTrainee_1();
        when(traineeDAO.findByUsername(mockedTrainee.getUser().getUsername())).thenReturn(mockedTrainee);

        traineeService.updateTrainee(mockedTrainee);
        verify(traineeDAO).update(mockedTrainee.getId(),mockedTrainee);
    }
    @Test
    void testDeleteTraineeByUsername() {
        Trainee mockedTrainee = TraineeMockData.getMockedTrainee_1();
        when(traineeDAO.findByUsername(mockedTrainee.getUser().getUsername())).thenReturn(mockedTrainee);

        traineeService.deleteTraineeByUsername(mockedTrainee.getUser().getUsername());
        verify(traineeDAO).deleteByUsername(mockedTrainee.getUser().getUsername());
    }
    @Test
    void testActivateDeactivateTrainee() {
        Trainee mockedTrainee = TraineeMockData.getMockedTrainee_1();
        when(traineeDAO.findByUsername(mockedTrainee.getUser().getUsername())).thenReturn(mockedTrainee);
        when(traineeDAO.findById(mockedTrainee.getId())).thenReturn(mockedTrainee);
        traineeService.activateDeactivateTrainee(mockedTrainee.getId(), mockedTrainee.getUser().isActive());

        verify(traineeDAO).save(mockedTrainee);
    }
    @Test
    void testChangePassword() {
        Trainee mockedTrainee = TraineeMockData.getMockedTrainee_1();
        when(traineeDAO.findById(mockedTrainee.getId())).thenReturn(mockedTrainee);
        when(traineeDAO.findByUsername(mockedTrainee.getUser().getUsername())).thenReturn(mockedTrainee);
        traineeService.changePassword(mockedTrainee.getId(),mockedTrainee.getUser().getPassword()
                ,"newPass");
        verify(traineeDAO).save(mockedTrainee);
    }
    @Test
    void testGetAllTrainees() {
        Trainee trainee1 = new Trainee();
        Trainee trainee2 = new Trainee();
        List<Trainee> allTrainees = List.of(trainee1, trainee2);
        when(traineeDAO.findAll()).thenReturn(allTrainees);

        List<Trainee> result = traineeService.getAllTrainees();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

}
