package com.epam.service.impl;

import com.epam.dao.TrainerDao;
import com.epam.dto.trainee.TraineeWithTraining;
import com.epam.dto.trainer.TrainerWithTraining;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.model.User;
import com.epam.service.TrainerService;
import com.epam.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TrainerServiceImpl implements TrainerService {
    @Autowired
    private TrainerDao trainerDao;
    @Autowired
    private UserUtils userUtils;


    public Trainer createTrainer(Trainer trainer) {
        try {
            String username = userUtils.generateUsername(trainer.getUser());
            String password = UserUtils.generateRandomPassword();
            if (trainer.getUser() != null) {
                trainer.getUser().setUsername(username);
                trainer.getUser().setPassword(password);

            }
            Trainer createdTrainer = trainerDao.save(trainer);

            log.info("Trainer created successfully. Username: {}, ID: {}", username, createdTrainer.getId());
            return createdTrainer;
        } catch (Exception e) {
            log.error("Error creating trainer. Details: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating trainer.", e);
        }
    }

    public Optional<Trainer> updateTrainer(Trainer trainer) {
        boolean areUsernameAndPasswordMatching = areUsernameAndPasswordMatching(trainer.getUser().getUsername(), trainer.getUser().getPassword());
        if (!areUsernameAndPasswordMatching) {
            log.info("Trainer update failed because username: {} and password: {} do not match.", trainer.getUser().getUsername(), trainer.getUser().getPassword());
            return Optional.empty();
        }

        Optional<Trainer> optionalTrainer = trainerDao.update(trainer);
        if (optionalTrainer.isPresent()) {
            log.info("Trainer updated successfully. Username: {}, ID: {}", trainer.getUser().getUsername(), trainer.getId());
        } else {
            log.warn("Trainer not found for update. Username: {}", trainer.getUser().getUsername());
        }
        return optionalTrainer;
    }

    public Optional<Trainer> getTrainerById(Integer trainerId) {
        Optional<Trainer> optionalTrainer = trainerDao.findById(trainerId);
        if (optionalTrainer.isEmpty()) {
            log.warn("Trainer with ID {} not found for activation/deactivation.", trainerId);
            return Optional.empty();
        }
        Trainer trainer = optionalTrainer.get();
        boolean areUsernameAndPasswordMatching = areUsernameAndPasswordMatching(trainer.getUser().getUsername(), trainer.getUser().getPassword());
        if (!areUsernameAndPasswordMatching) {
            log.info("Trainer update failed because username: {} and password: {} do not match.", trainer.getUser().getUsername(), trainer.getUser().getPassword());
            return Optional.empty();
        }
        return optionalTrainer;
    }

    public List<Trainer> getAllTrainer() {
        return new ArrayList<>(trainerDao.findAll());
    }

    public boolean deleteTrainer(Integer trainerId) {
        Optional<Trainer> optionalTrainer = trainerDao.findById(trainerId);
        if (optionalTrainer.isEmpty()) {
            log.warn("Trainer with id {} not found. Deletion aborted.", trainerId);
            return false;
        }

        Trainer trainer = optionalTrainer.get();
        boolean areUsernameAndPasswordMatching = areUsernameAndPasswordMatching(trainer.getUser().getUsername(), trainer.getUser().getPassword());
        if (!areUsernameAndPasswordMatching) {
            log.info("Trainer deletion failed because username: {} and password: {} do not match for trainer id :{}",
                    trainer.getUser().getUsername(), trainer.getUser().getPassword(), trainerId);
        }
        return trainerDao.delete(trainerId);

    }

    public boolean changePassword(Integer trainerId, String currentPassword, String newPassword) {
        Optional<Trainer> optionalTrainer = trainerDao.findById(trainerId);
        if (optionalTrainer.isEmpty()) {
            return false;
        }
        Trainer trainer = optionalTrainer.get();

        boolean areUsernameAndPasswordMatching = areUsernameAndPasswordMatching(trainer.getUser().getUsername(), trainer.getUser().getPassword());
        if (areUsernameAndPasswordMatching && currentPassword.equals(trainer.getUser().getPassword())) {
            trainer.getUser().setPassword(newPassword);
            trainerDao.save(trainer);
            return true;
        }
        return false;
    }

    public void activateTrainer(Integer trainerId) {
        Optional<Trainer> optionalTrainer = trainerDao.findById(trainerId);
        if (optionalTrainer.isEmpty()) {
            log.warn("Trainer with ID {} not found for activation", trainerId);
            return;
        }

        Trainer trainer = optionalTrainer.get();
        User user = trainer.getUser();

        boolean areUsernameAndPasswordMatching = areUsernameAndPasswordMatching(user.getUsername(), user.getPassword());
        if (!areUsernameAndPasswordMatching) {
            log.warn("Username and password do not match for trainer with ID {}. Activation aborted.", trainerId);
            return;
        }

        user.setActive(true);
        trainerDao.update(trainer);
        log.info("Trainer with ID {}, activated successfully.", trainerId);
    }

    public void deactivateTrainer(Integer trainerId) {
        Optional<Trainer> optionalTrainer = trainerDao.findById(trainerId);
        if (optionalTrainer.isEmpty()) {
            log.warn("Trainer with ID {} not found for deactivation.", trainerId);
            return;
        }

        Trainer trainer = optionalTrainer.get();
        User user = trainer.getUser();

        boolean areUsernameAndPasswordMatching = areUsernameAndPasswordMatching(user.getUsername(), user.getPassword());
        if (!areUsernameAndPasswordMatching) {
            log.warn("Username and password do not match for trainer with ID {}. Deactivation aborted.", trainerId);
            return;
        }

        user.setActive(false);
        trainerDao.update(trainer);
        log.info("Trainer with ID {}, deactivated successfully.", trainerId);
    }

    public boolean areUsernameAndPasswordMatching(String username, String password) {
        Assert.notNull(username, "Username must not be null");
        Assert.notNull(password, "Password must not be null");
        Optional<Trainer> optionalTrainer = trainerDao.findByUsername(username);

        if (optionalTrainer.isPresent() && optionalTrainer.get().getUser() != null) {
            return optionalTrainer.get().getUser().getPassword().equals(password);
        } else {
            return false;
        }
    }

    @Override
    public Optional<Trainer> getTrainerByUsername(String username) {
        Optional<Trainer> optionalTrainer =trainerDao.findByUsername(username);
        if (optionalTrainer.isPresent()) {
            Trainer trainer = optionalTrainer.get();
            boolean areUsernameAndPasswordMatching = areUsernameAndPasswordMatching(trainer.getUser().getUsername(), trainer.getUser().getPassword());
            if (areUsernameAndPasswordMatching) {
                return optionalTrainer;
            }
        }
        return Optional.empty();

    }

    @Override
    public List<Training> getTrainerTrainingsList(TrainerWithTraining trainerWithTraining) {
        return trainerDao.getTrainerTrainingsListByCriteria(trainerWithTraining);
    }

}
