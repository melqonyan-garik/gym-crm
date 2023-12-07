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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@Slf4j
public class TraineeDao {
    @PersistenceContext
    EntityManager entityManager;

    public Trainee save(Trainee trainee) {
        entityManager.persist(trainee);
        return trainee;
    }

    public Optional<Trainee> update(Trainee updatedEntity) {
        Trainee mergedTrainee = entityManager.merge(updatedEntity);
        return Optional.ofNullable(mergedTrainee);
    }

    public Optional<Trainee> findById(Integer id) {
        Trainee trainee = entityManager.find(Trainee.class, id);
        return Optional.ofNullable(trainee);
    }

    public Optional<Trainee> findByUsername(String username) {
        TypedQuery<Trainee> query = entityManager.createQuery("""
                SELECT tr FROM Trainee tr
                INNER JOIN User u ON u.id = tr.user.id AND u.username LIKE CONCAT('%', :username, '%')
                """, Trainee.class).setParameter("username", username);

        return Optional.ofNullable(query.getSingleResult());
    }


    public List<Trainee> findAll() {
        Query query = entityManager.createQuery("SELECT t FROM Trainee t");
        return query.getResultList();
    }

    public boolean delete(Integer traineeId) {
        Optional<Trainee> optionalTrainee = findById(traineeId);
        if (optionalTrainee.isPresent()) {
            entityManager.remove(optionalTrainee.get());
            log.info("Trainee with ID {} deleted successfully.", traineeId);
            return true;
        }

        log.warn("Trainee with ID {} not found for deletion.", traineeId);
        return false;
    }

    public boolean deleteByUsername(String username) {
        Optional<Trainee> optionalTrainee = findByUsername(username);
        if (optionalTrainee.isPresent()) {
            entityManager.remove(optionalTrainee.get());
            log.info("Trainee with username {} deleted successfully.", username);
            return true;
        }

        log.info("Trainee with username {} not found for deletion.", username);
        return false;
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
        } else {
            log.warn("No trainee found for ID {}. Unable to update trainers list.", traineeId);
        }

    }

}

