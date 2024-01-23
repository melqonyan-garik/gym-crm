package com.epam.service.impl;

import com.epam.dao.TraineeDao;
import com.epam.dto.trainee.TraineeWithTraining;
import com.epam.exceptions.WrongPasswordException;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.model.User;
import com.epam.service.TraineeService;
import com.epam.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TraineeServiceImpl implements TraineeService {
    @Autowired
    private TraineeDao traineeDao;
    @Autowired
    private UserUtils userUtils;

    public Trainee createTrainee(Trainee trainee) {
        try {
            String username = userUtils.generateUsername(trainee.getUser().getFirstname(), trainee.getUser().getLastname());
            String password = UserUtils.generateRandomPassword();
            if (trainee.getUser() != null) {
                trainee.getUser().setUsername(username);
                trainee.getUser().setPassword(password);

            }
            Trainee createdTrainee = traineeDao.save(trainee);

            log.info("Trainee created successfully. Username: {}, ID: {}", username, createdTrainee.getId());
            return createdTrainee;
        } catch (Exception e) {
            log.error("Error creating trainee. Details: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating trainee.", e);
        }
    }

    public Optional<Trainee> updateTrainee(Trainee trainee) {
        boolean areUsernameAndPasswordMatching = areUsernameAndPasswordMatching(trainee.getUser().getUsername(), trainee.getUser().getPassword());
        if (!areUsernameAndPasswordMatching) {
            throw new WrongPasswordException("Password don't match");
        }

        Optional<Trainee> optionalTrainee = traineeDao.update(trainee);
        if (optionalTrainee.isPresent()) {
            log.info("Trainee updated successfully. Username: {}, ID: {}", trainee.getUser().getUsername(), trainee.getId());
        } else {
            log.warn("Trainee not found for update. Username: {}", trainee.getUser().getUsername());
        }
        return optionalTrainee;

    }

    public Optional<Trainee> getTraineeById(Integer traineeId) {
        Optional<Trainee> optionalTrainee = traineeDao.findById(traineeId);
        if (optionalTrainee.isEmpty()) {
            log.warn("Trainee with ID {} not found for activation/deactivation.", traineeId);
            return Optional.empty();
        }
        Trainee trainee = optionalTrainee.get();
        boolean areUsernameAndPasswordMatching = areUsernameAndPasswordMatching(trainee.getUser().getUsername(), trainee.getUser().getPassword());
        if (!areUsernameAndPasswordMatching) {
            throw new WrongPasswordException("Password don't match");
        }
        return optionalTrainee;
    }

    public Optional<Trainee> getTraineeByUsername(String username) {
        Optional<Trainee> optionalTrainee = traineeDao.findByUsername(username);
        if (optionalTrainee.isPresent()) {
            Trainee trainee = optionalTrainee.get();
            boolean areUsernameAndPasswordMatching = areUsernameAndPasswordMatching(trainee.getUser().getUsername(), trainee.getUser().getPassword());
            if (areUsernameAndPasswordMatching) {
                return optionalTrainee;
            } else {
                throw new WrongPasswordException("Password don't match");
            }
        }
        return Optional.empty();
    }

    public List<Trainee> getAllTrainees() {
        return new ArrayList<>(traineeDao.findAll());
    }

    public boolean deleteTrainee(Integer traineeId) {
        Optional<Trainee> optionalTrainee = traineeDao.findById(traineeId);
        if (optionalTrainee.isEmpty()) {
            log.warn("Trainee with ID {} not found for deletion.", traineeId);
            return false;
        }
        Trainee trainee = optionalTrainee.get();
        boolean areUsernameAndPasswordMatching = areUsernameAndPasswordMatching(trainee.getUser().getUsername(), trainee.getUser().getPassword());
        if (!areUsernameAndPasswordMatching) {
            throw new WrongPasswordException("Password don't match");
        }

        return traineeDao.delete(traineeId);

    }

    public boolean deleteTraineeByUsername(String username) {
        Optional<Trainee> optionalTrainee = traineeDao.findByUsername(username);
        if (optionalTrainee.isEmpty()) {
            log.warn("Trainee with username {} not found. Deletion aborted.", username);
            return false;
        }
        Trainee trainee = optionalTrainee.get();
        boolean areUsernameAndPasswordMatching = areUsernameAndPasswordMatching(trainee.getUser().getUsername(), trainee.getUser().getPassword());
        if (!areUsernameAndPasswordMatching) {
            throw new WrongPasswordException("Password don't match");
        }
        return traineeDao.deleteByUsername(username);

    }

    public boolean changePassword(Integer traineeId, String currentPassword, String newPassword) {
        Optional<Trainee> optionalTrainee = traineeDao.findById(traineeId);
        if (optionalTrainee.isEmpty()) {
            return false;
        }
        Trainee trainee = optionalTrainee.get();
        boolean areUsernameAndPasswordMatching = areUsernameAndPasswordMatching(trainee.getUser().getUsername(), trainee.getUser().getPassword());

        if (areUsernameAndPasswordMatching && currentPassword.equals(trainee.getUser().getPassword())) {
            trainee.getUser().setPassword(newPassword);
            traineeDao.save(trainee);
            return true;
        }
        return false;
    }

    @Transactional
    public void activateTrainee(Integer traineeId) {
        Optional<Trainee> optionalTrainee = traineeDao.findById(traineeId);
        if (optionalTrainee.isEmpty()) {
            log.warn("Trainee with ID {} not found for activation.", traineeId);
            return;
        }

        Trainee trainee = optionalTrainee.get();
        User user = trainee.getUser();

        boolean areUsernameAndPasswordMatching = areUsernameAndPasswordMatching(user.getUsername(), user.getPassword());
        if (!areUsernameAndPasswordMatching) {
            throw new WrongPasswordException("Password don't match");
        }

        user.setActive(true);
        trainee.setUser(user);
        traineeDao.update(trainee);
        log.info("Trainee with ID {}, activated successfully.", traineeId);

    }

    @Transactional
    public void deactivateTrainee(Integer traineeId) {
        Optional<Trainee> optionalTrainee = traineeDao.findById(traineeId);
        if (optionalTrainee.isEmpty()) {
            log.warn("Trainee with ID {} not found for deactivation.", traineeId);
            return;
        }

        Trainee trainee = optionalTrainee.get();
        User user = trainee.getUser();

        boolean areUsernameAndPasswordMatching = areUsernameAndPasswordMatching(user.getUsername(), user.getPassword());
        if (!areUsernameAndPasswordMatching) {
            throw new WrongPasswordException("Password don't match");
        }

        user.setActive(false);
        trainee.setUser(user);
        traineeDao.update(trainee);
        log.info("Trainee with ID {}, deactivated successfully.", traineeId);

    }

    public boolean areUsernameAndPasswordMatching(String username, String password) {
        Assert.notNull(username, "Username must not be null");
        Assert.notNull(password, "Password must not be null");
        Optional<Trainee> optionalTrainee = traineeDao.findByUsername(username);

        if (optionalTrainee.isPresent() && optionalTrainee.get().getUser() != null) {
            return optionalTrainee.get().getUser().getPassword().equals(password);
        } else {
            return false;
        }

    }

    @Override
    public List<Trainer> getTrainersByTraineeId(Integer id) {
        Optional<Trainee> optionalTrainee = getTraineeById(id);
        if (optionalTrainee.isPresent()) {
            Trainee trainee = optionalTrainee.get();
            return trainee.getTrainers();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Trainer> getNotAssignedTrainers(String username) {
        return traineeDao.getNotAssignedTrainers(username);
    }

    @Override
    public List<Training> getTraineeTrainingsList(TraineeWithTraining traineeWithTraining) {
        return traineeDao.getTraineeTrainingsListByCriteria(traineeWithTraining);
    }

}

