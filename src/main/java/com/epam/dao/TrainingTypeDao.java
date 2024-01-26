package com.epam.dao;

import com.epam.model.TrainingType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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
