package com.epam.dao;

import com.epam.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Transactional
public class UserDao {
    @PersistenceContext
    EntityManager entityManager;

    public Optional<User> update(User updatedEntity) {
        User mergedTrainee = entityManager.merge(updatedEntity);
        return Optional.ofNullable(mergedTrainee);
    }

    public Set<String> getAllUsernames() {
        return entityManager.createQuery("SELECT u.username FROM User u", String.class)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    public Optional<User> findByUsername(String username) {
        User user = entityManager.createQuery("Select u From User u where u.usarname like '%:username%'",
                        User.class)
                .setParameter("username", username)
                .getSingleResult();
        return Optional.ofNullable(user);
    }
}
