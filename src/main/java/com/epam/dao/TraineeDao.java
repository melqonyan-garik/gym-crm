package com.epam.dao;

import com.epam.dto.trainee.TraineeWithTraining;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.service.GeneralService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@Slf4j
public class TraineeDao extends GeneralService {

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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trainee> cq = cb.createQuery(Trainee.class);
        Root<Trainee> traineeRoot = cq.from(Trainee.class);
        cq.select(traineeRoot).where(cb.like(traineeRoot.get("user").get("username"), username));

        Trainee trainee = entityManager.createQuery(cq).getResultList().get(0);
        return Optional.ofNullable(trainee);
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

    public List<Trainer> getNotAssignedTrainers(String username) {
//        Optional<Trainee> optionalTrainee = findByUsername(username);
//        if (optionalTrainee.isPresent()) {
//            Trainee trainee = optionalTrainee.get();
//            List<Trainer> assignedTrainers = trainee.getTrainers();
//            List<Trainer> allTrainers = trainerDao.findAll();
//            return allTrainers.stream()
//                    .filter(trainer -> !assignedTrainers.contains(trainer))
//                    .collect(Collectors.toList());
//
//        }
        return Collections.emptyList();
    }

    public List<Training> getTraineeTrainingsListByCriteria(TraineeWithTraining traineeWithTraining) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> cq = cb.createQuery(Training.class);
        Root<Training> trainingRoot = cq.from(Training.class);
        Join<Training, Trainer> trainersJoin = trainingRoot.join("trainer", JoinType.INNER);
        Join<Training, Trainee> traineeJoin = trainingRoot.join("trainee", JoinType.INNER);


        List<Predicate> predicates = new ArrayList<>();
        addLikePredicate(traineeJoin.get("user").get("username"), traineeWithTraining.getUsername(), cb, predicates);
        addLikePredicate(trainersJoin.get("user").get("username"), traineeWithTraining.getTrainerName(), cb, predicates);
        addLikePredicate(trainingRoot.get("trainingName"), traineeWithTraining.getTrainingType(), cb, predicates);
        addGreaterThanOrEqualsPredicate(trainingRoot.get("trainingDate"), traineeWithTraining.getPeriodFrom(), cb, predicates);
        addLessThanOrEqualsPredicate(trainingRoot.get("trainingDate"), traineeWithTraining.getPeriodTo(), cb, predicates);
        CriteriaQuery<Training> where = cq.select(trainingRoot).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(where).getResultList();
    }


}

