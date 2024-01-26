package com.epam.dao;

import com.epam.model.Training;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
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
        Query query = entityManager.createQuery("SELECT t FROM Training t");
        return query.getResultList();
    }

    public boolean delete(Integer trainingId) {
        Optional<Training> optionalTraining = findById(trainingId);
        if (optionalTraining.isPresent()) {
            entityManager.remove(optionalTraining.get());
            log.info("Training with ID {} deleted successfully.", trainingId);
            return true;
        }

        log.warn("Training with ID {} not found for deletion.", trainingId);
        return false;
    }
}
