package com.epam.dao;

import com.epam.dto.trainer.TrainerWithTraining;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.service.GeneralService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@Slf4j
public class TrainerDao extends GeneralService {


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
        TypedQuery<Trainer> query = entityManager.createQuery("SELECT tr FROM Trainer tr" +
                                                              " INNER JOIN User u ON u.id = tr.user.id" +
                                                              " AND u.username LIKE :username", Trainer.class)
                .setParameter("username", username);
        return Optional.ofNullable(query.getSingleResult());
    }

    public List<Trainer> findAll() {
        Query query = entityManager.createQuery("SELECT t FROM Trainer t");
        return query.getResultList();
    }

    public boolean delete(Integer trainerId) {
        Optional<Trainer> optionalTrainer = findById(trainerId);
        if (optionalTrainer.isPresent()) {
            entityManager.remove(optionalTrainer.get());
            log.info("Trainer with ID {} deleted successfully.", trainerId);
            return true;
        }

        log.warn("Trainer with ID {} not found for deletion.", trainerId);
        return false;
    }

    public void updateTrainerTraineeList(Integer trainerId, List<Trainee> newTrainees) {
        Trainer trainer = entityManager.find(Trainer.class, trainerId);
        if (trainer != null) {
            trainer.getTrainees().forEach(trainee -> trainee.getTrainers().remove(trainer));
            trainer.getTrainees().clear();

            newTrainees.forEach(newTrainee -> newTrainee.getTrainers().add(trainer));
            trainer.setTrainees(newTrainees);
        } else {
            log.warn("No trainer found for ID {}. Unable to update trainers list.", trainerId);
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

    public List<Training> getTrainerTrainingsListByCriteria(TrainerWithTraining trainerWithTraining) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> cq = cb.createQuery(Training.class);
        Root<Training> trainingRoot = cq.from(Training.class);
        Join<Training, Trainer> trainersJoin = trainingRoot.join("trainer", JoinType.INNER);
        Join<Training, Trainee> traineeJoin = trainingRoot.join("trainee", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();
        addLikePredicate(trainersJoin.get("user").get("username"), trainerWithTraining.getUsername(), cb, predicates);
        addLikePredicate(traineeJoin.get("user").get("username"), trainerWithTraining.getTraineeName(), cb, predicates);
        addGreaterThanOrEqualsPredicate(trainingRoot.get("trainingDate"), trainerWithTraining.getPeriodFrom(), cb, predicates);
        addLessThanOrEqualsPredicate(trainingRoot.get("trainingDate"), trainerWithTraining.getPeriodTo(), cb, predicates);
        CriteriaQuery<Training> where = cq.select(trainingRoot).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(where).getResultList();
    }
}
