package com.epam.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDaoTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private UserDao userDao;

    @Test
    void testGetAllUsernames() {
        // Given
        Set<String> expectedUsernames = new HashSet<>(List.of("user1", "user2"));

        // Mocking
        TypedQuery query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(String.class))).thenReturn(query);
        when(query.getResultStream()).thenReturn(expectedUsernames.stream());

        // When
        Set<String> result = userDao.getAllUsernames();

        // Then
        assertNotNull(result);
        assertEquals(expectedUsernames, result);

        // Optionally, you can add more assertions based on the expected behavior
        verify(entityManager).createQuery(anyString(), eq(String.class));
        verify(query).getResultStream();
    }
}
