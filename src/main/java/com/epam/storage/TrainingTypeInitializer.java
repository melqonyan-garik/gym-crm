package com.epam.storage;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TrainingTypeInitializer {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void initializeTrainingTypes() {
        entityManager.createNativeQuery("INSERT INTO training_type (id, training_type_name) VALUES (1, 'Technical')")
                .executeUpdate();
        entityManager.createNativeQuery("INSERT INTO training_type (id, training_type_name) VALUES (2, 'Soft Skills')")
                .executeUpdate();
        entityManager.createNativeQuery("INSERT INTO training_type (id, training_type_name) VALUES (3, 'Leadership')")
                .executeUpdate();
    }
}
