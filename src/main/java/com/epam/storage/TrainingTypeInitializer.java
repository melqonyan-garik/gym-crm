package com.epam.storage;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class TrainingTypeInitializer {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void initializeTrainingTypes() {
        entityManager.createNativeQuery("INSERT INTO training_type (training_type_name) " +
                        "VALUES ('Technical'), ('Soft Skills'), ('Leadership')")
                .executeUpdate();
    }
}
