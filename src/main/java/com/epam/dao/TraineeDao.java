package com.epam.dao;

import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class TraineeDao {
    @PersistenceContext
    EntityManager entityManager;

    public void save(Trainee trainee) {
        entityManager.persist(trainee);
    }

    public boolean update(Integer id, Trainee updatedEntity) {
        Trainee trainee = findById(id);
        if (trainee != null) {
            trainee.setAddress(updatedEntity.getAddress());
            trainee.setDateOfBirth(updatedEntity.getDateOfBirth());
            trainee.setUser(updatedEntity.getUser());
            trainee.setTrainings(updatedEntity.getTrainings());
            updateTraineeTrainersList(id,updatedEntity.getTrainers());
            return true;
        }
        return false;
    }

    public Trainee findById(Integer id) {
        return entityManager.find(Trainee.class, id);
    }

    public Trainee findByUsername(String username) {
        TypedQuery<Trainee> query = entityManager.createQuery("""
                SELECT tr FROM Trainee tr
                INNER JOIN User u ON u.id = tr.user.id AND u.username LIKE CONCAT('%', :username, '%')
                """, Trainee.class).setParameter("username", username);

        return query.getSingleResult();
    }


    public List<Trainee> findAll() {
        Query query = entityManager.createQuery("""
                SELECT t FROM Trainee t
                """);
        return query.getResultList();
    }

    public void delete(Integer id) {
        Trainee trainee = findById(id);
        entityManager.remove(trainee);
    }

    public void deleteByUsername(String username) {
        Trainee trainee = findByUsername(username);
        entityManager.remove(trainee);
    }

    List<Training> getTrainingsByUsername(String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trainee> cq = cb.createQuery(Trainee.class);
        Root<Trainee> traineeRoot = cq.from(Trainee.class);
        cq.select(traineeRoot).where(cb.like(traineeRoot.get("user").get("username"), username));
        Trainee trainee = entityManager.createQuery(cq).getSingleResult();//username is unique that's why I choose single result
        return trainee.getTrainings();
    }

    public void updateTraineeTrainersList(Integer traineeId, List<Trainer> newTrainers) {
        Trainee trainee = entityManager.find(Trainee.class, traineeId);

        if (trainee != null) {
            trainee.getTrainers()
                    .forEach(trainer -> trainer.getTrainees().remove(trainee));
            trainee.getTrainers().clear();
            newTrainers.forEach(newTrainer -> newTrainer.getTrainees().add(trainee));
            trainee.setTrainers(newTrainers);
        }
    }

}

