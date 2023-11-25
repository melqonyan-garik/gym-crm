package com.epam.dao;

import com.epam.model.Trainer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainerDao {

    @PersistenceContext
    EntityManager entityManager;


    public void save(Trainer trainer) {
        entityManager.persist(trainer);
    }


    public Trainer findById(Integer id) {
        return entityManager.find(Trainer.class, id);
    }


    public List<Trainer> findAll() {
        String jpql = "SELECT t FROM Trainer t";
        Query query = entityManager.createQuery(jpql);
        return query.getResultList();
    }


    public void update(Integer id, Trainer updatedEntity) {
        Trainer trainer = findById(id);
        trainer.setUser(updatedEntity.getUser());
        trainer.setSpecialization(updatedEntity.getSpecialization());
        trainer.setTrainees(updatedEntity.getTrainees());
        trainer.setTrainings(updatedEntity.getTrainings());
    }


    public void delete(Integer id) {
        entityManager.remove(findById(id));
    }
}
