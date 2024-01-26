package com.epam.utils;

import com.epam.dao.UserDao;
import com.epam.model.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class UserUtilsTest {
    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserUtils userUtils;



    @Test
    @Disabled
    public void testGenerateRandomPassword() {
        String password = UserUtils.generateRandomPassword();
        assertEquals(10, password.length());
    }

    @Test
    void testGenerateUsername() {
        User user = new User();
        user.setFirstname("john");
        user.setLastname("doe");

        when(userDao.getAllUsernames()).thenReturn(Set.of("john.doe", "john.doe1", "john.doe2"));
        String generatedUsername = userUtils.generateUsername(user.getFirstname(),user.getLastname());

        assertEquals("john.doe3", generatedUsername);
        verify(userDao).getAllUsernames();
    }
}
