package com.epam.dao;

import com.epam.model.TrainingType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class TrainingTypeDao {
    @PersistenceContext
    EntityManager entityManager;

    public List<TrainingType> getAllTrainingTypes() {
        return entityManager.createQuery("SELECT t FROM TrainingType t", TrainingType.class)
                .getResultList();
    }

}
