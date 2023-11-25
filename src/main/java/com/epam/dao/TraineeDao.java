package com.epam.dao;

import com.epam.model.Trainee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class TraineeDao {
    @PersistenceContext
    EntityManager entityManager;

    public void save(Trainee trainee) {
        entityManager.persist(trainee);
    }

    public Trainee findById(Integer id) {
        return entityManager.find(Trainee.class, id);
    }

    public List<Trainee> findAll() {
        String jpql = "SELECT t FROM Trainee t";
        Query query = entityManager.createQuery(jpql);
        return query.getResultList();
    }

    public void update(Integer id, Trainee updatedEntity) {
        Trainee trainee = findById(id);
        trainee.setAddress(updatedEntity.getAddress());
        trainee.setDateOfBirth(updatedEntity.getDateOfBirth());
        trainee.setUser(updatedEntity.getUser());
        trainee.setTrainers(updatedEntity.getTrainers());
        trainee.setTrainings(updatedEntity.getTrainings());

    }

    public void delete(Integer id) {
        entityManager.remove(findById(id));
    }
}

