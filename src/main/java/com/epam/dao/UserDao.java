package com.epam.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Transactional
public class UserDao {
    @PersistenceContext
    EntityManager entityManager;

    public Set<String> getAllUsernames() {
         return entityManager.createQuery("""
                         SELECT u.username FROM User u
                         """, String.class)
                 .getResultStream()
                 .collect(Collectors.toSet());
    }
}
