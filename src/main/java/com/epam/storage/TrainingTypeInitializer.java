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
        entityManager.createNativeQuery("INSERT INTO training_type (training_type_name) " +
                        "VALUES ('Technical'), ('Soft Skills'), ('Leadership')")
                .executeUpdate();
    }
}
