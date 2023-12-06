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
import java.util.Optional;

@Repository
@Transactional
public class TrainerDao {

    @PersistenceContext
    EntityManager entityManager;

    public Trainer save(Trainer trainer) {
        entityManager.persist(trainer);
        return trainer;
    }

    public Optional<Trainer> update(Trainer trainer) {
        Trainer updatedTrainer = entityManager.merge(trainer);
        return Optional.ofNullable(updatedTrainer);
    }

    public Optional<Trainer> findById(Integer id) {
        Trainer trainer = entityManager.find(Trainer.class, id);
        return Optional.ofNullable(trainer);
    }

    public Optional<Trainer> findByUsername(String username) {
        TypedQuery<Trainer> query = entityManager.createQuery("""
                SELECT tr FROM Trainer tr
                INNER JOIN User u ON u.id = tr.user.id AND u.username LIKE CONCAT('%', :username, '%')
                """, Trainer.class).setParameter("username", username);

        return Optional.ofNullable(query.getSingleResult());
    }

    public List<Trainer> findAll() {
        Query query = entityManager.createQuery("""
                SELECT t FROM Trainer t
                """);
        return query.getResultList();
    }

    public boolean delete(Integer trainerId) {
        Optional<Trainer> optionalTrainer = findById(trainerId);
        if (optionalTrainer.isPresent()) {
            entityManager.remove(optionalTrainer.get());
            return true;
        }
        return false;
    }

    public void updateTrainerTraineeList(Integer trainerId, List<Trainee> newTrainees) {
        Trainer trainer = entityManager.find(Trainer.class, trainerId);
        if (trainer != null) {
            trainer.getTrainees().forEach(trainee -> trainee.getTrainers().remove(trainer));
            trainer.getTrainees().clear();

            newTrainees.forEach(newTrainee -> newTrainee.getTrainers().add(trainer));
            trainer.setTrainees(newTrainees);
        }
    }

    List<Training> getTrainingsByUsername(String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trainer> cq = cb.createQuery(Trainer.class);
        Root<Trainer> trainerRoot = cq.from(Trainer.class);
        cq.select(trainerRoot).where(cb.like(trainerRoot.get("user").get("username"), username));
        Trainer trainer = entityManager.createQuery(cq).getSingleResult();//username is unique that's why we choose single result
        return trainer.getTrainings();
    }

    public List<Trainer> getNotAssignedActiveTrainers() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trainer> cq = cb.createQuery(Trainer.class);
        Root<Trainer> trainerRoot = cq.from(Trainer.class);

        cq.select(trainerRoot)
                .where(cb.and(
                        cb.isEmpty(trainerRoot.get("trainees")),
                        cb.isTrue(trainerRoot.get("user").get("isActive"))
                ));

        return entityManager.createQuery(cq).getResultList();
    }

}
