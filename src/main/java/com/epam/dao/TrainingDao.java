package com.epam.dao;

import com.epam.model.Training;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainingDao {

    @PersistenceContext
    EntityManager entityManager;


    public Training save(Training training) {
        entityManager.persist(training);
        return training;
    }

    public Optional<Training> update(Training updatedEntity) {
        entityManager.merge(updatedEntity);
        return Optional.ofNullable(updatedEntity);
    }

    public Optional<Training> findById(Integer id) {
        Training training = entityManager.find(Training.class, id);
        return Optional.ofNullable(training);
    }

    public List<Training> findAll() {
        Query query = entityManager.createQuery("""
                SELECT t FROM Training t
                """);
        return query.getResultList();
    }

    public boolean delete(Integer id) {
        Optional<Training> optionalTraining = findById(id);
        if (optionalTraining.isPresent()) {
            entityManager.remove(optionalTraining.get());
            return true;
        }
        return false;
    }
}
