package com.epam.controller;

import com.epam.dao.UserDao;
import com.epam.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LoginControllerTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testLogin_Successful() {
//        // Mocking data
//        User user = new User();
//        user.setUsername("testUser");
//        user.setPassword("correctPassword");
//        when(userDao.findByUsername("testUser")).thenReturn(Optional.of(user));
//
//        // Call the controller method
//        ResponseEntity<String> responseEntity = loginController.login("testUser", "correctPassword");
//
//        // Verify the response
//        assertEquals(200, responseEntity.getStatusCodeValue());
//        assertEquals("Login successful. Status: 200 OK", responseEntity.getBody());
//    }
//
//    @Test
//    void testLogin_Unsuccessful() {
//        // Mocking data
//        when(userDao.findByUsername("nonExistentUser")).thenReturn(Optional.empty());
//
//        // Call the controller method
//        ResponseEntity<String> responseEntity = loginController.login("nonExistentUser", "somePassword");
//
//        // Verify the response
//        assertEquals(401, responseEntity.getStatusCodeValue());
//        assertEquals("Invalid credentials. Status: 401 Unauthorized", responseEntity.getBody());
//    }

    @Test
    @Disabled
    void testChangePassword_Successful() {
        // Mocking data
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("oldPassword");
        when(userDao.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(userDao.update(user)).thenReturn(Optional.of(user));

        // Call the controller method
        ResponseEntity<String> responseEntity = loginController.changePassword("testUser", "oldPassword", "newPassword");

        // Verify the response
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("Password changed successfully. Status: 200 OK", responseEntity.getBody());
        assertEquals("newPassword", user.getPassword());
    }

    @Test
    @Disabled
    void testChangePassword_Unsuccessful() {
        // Mocking data
        when(userDao.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

        // Call the controller method
        ResponseEntity<String> responseEntity = loginController.changePassword("nonExistentUser", "somePassword", "newPassword");

        // Verify the response
        assertEquals(401, responseEntity.getStatusCodeValue());
        assertEquals("Invalid credentials. Status: 401 Unauthorized", responseEntity.getBody());
    }
}
