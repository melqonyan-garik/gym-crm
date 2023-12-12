package com.epam.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Transactional
public class UserDao {
    @PersistenceContext
    EntityManager entityManager;

    public Set<String> getAllUsernames() {
         return entityManager.createQuery("SELECT u.username FROM User u", String.class)
                 .getResultStream()
                 .collect(Collectors.toSet());
    }
}
