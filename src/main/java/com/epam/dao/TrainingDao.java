package com.epam.dao;

import com.epam.model.Training;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainingDao {

    @PersistenceContext
    EntityManager entityManager;


    public void save(Training training) {
        entityManager.persist(training);
    }


    public Training findById(Integer id) {
        return entityManager.find(Training.class, id);
    }


    public List<Training> findAll() {
        Query query = entityManager.createQuery("""
                SELECT t FROM Training t
                """);
        return query.getResultList();
    }


    public void update(Integer id, Training updatedEntity) {
        Training training = findById(id);
        training.setTrainingName(updatedEntity.getTrainingName());
        training.setTrainingType(updatedEntity.getTrainingType());
        training.setTrainingDate(updatedEntity.getTrainingDate());
        training.setTrainingDuration(updatedEntity.getTrainingDuration());
        training.setTrainee(updatedEntity.getTrainee());
        training.setTrainer(updatedEntity.getTrainer());

    }


    public void delete(Integer id) {
        entityManager.remove(findById(id));
    }
}
