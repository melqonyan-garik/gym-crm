package com.epam.service.impl;

import com.epam.dao.TraineeDao;
import com.epam.model.Trainee;
import com.epam.model.User;
import com.epam.service.TraineeService;
import com.epam.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
        String username = userUtils.generateUsername(trainee.getUser());
        String password = UserUtils.generateRandomPassword();
        if (trainee.getUser() != null) {
            trainee.getUser().setUsername(username);
            trainee.getUser().setPassword(password);

        }
        return traineeDao.save(trainee);
    }

    public Optional<Trainee> updateTrainee(Trainee trainee) {
        areUsernameAndPasswordMatching(trainee.getUser().getUsername(), trainee.getUser().getPassword());
        return traineeDao.update(trainee);
    }

    public Optional<Trainee> getTraineeById(Integer traineeId) {
        Optional<Trainee> optionalTrainee = traineeDao.findById(traineeId);
        if (optionalTrainee.isPresent()) {
            Trainee trainee = optionalTrainee.get();
            boolean areUsernameAndPasswordMatching = areUsernameAndPasswordMatching(trainee.getUser().getUsername(), trainee.getUser().getPassword());
            if (areUsernameAndPasswordMatching) {
                return optionalTrainee;
            }
        }
        return Optional.empty();
    }

    public Optional<Trainee> getTraineeByUsername(String username) {
        Optional<Trainee> optionalTrainee = traineeDao.findByUsername(username);
        if (optionalTrainee.isPresent()) {
            Trainee trainee = optionalTrainee.get();
            boolean areUsernameAndPasswordMatching = areUsernameAndPasswordMatching(trainee.getUser().getUsername(), trainee.getUser().getPassword());
            if (areUsernameAndPasswordMatching) {
                return optionalTrainee;
            }
        }
        return Optional.empty();
    }

    public List<Trainee> getAllTrainees() {
        return traineeDao.findAll()
                .stream()
                .toList();
    }

    public boolean deleteTrainee(Integer traineeId) {
        getTraineeById(traineeId);
        return traineeDao.delete(traineeId);
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

    public void activateDeactivateTrainee(Integer traineeId, boolean activate) {
        Optional<Trainee> optionalTrainee = traineeDao.findById(traineeId);
        if (optionalTrainee.isPresent()) {
            Trainee trainee = optionalTrainee.get();
            User user = trainee.getUser();
            areUsernameAndPasswordMatching(user.getUsername(), user.getPassword());
            user.setActive(activate);
            traineeDao.save(trainee);
        }

    }

    public boolean deleteTraineeByUsername(String username) {
        Optional<Trainee> optionalTrainee = traineeDao.findByUsername(username);
        if (optionalTrainee.isEmpty()) {
            return false;
        }
        Trainee trainee = optionalTrainee.get();
        boolean areUsernameAndPasswordMatching = areUsernameAndPasswordMatching(trainee.getUser().getUsername(), trainee.getUser().getPassword());
        if (areUsernameAndPasswordMatching) {
            traineeDao.deleteByUsername(username);
            return true;
        }
        return false;
    }

    public boolean areUsernameAndPasswordMatching(String username, String password) {
        Assert.notNull(username, "Username must not be null");
        Assert.notNull(password, "Password must not be null");
        Optional<Trainee> optionalTrainee = traineeDao.findByUsername(username);

        if (optionalTrainee.isPresent() && optionalTrainee.get().getUser() != null) {
            return optionalTrainee.get().getUser().getPassword().equals(password);
        } else {
            log.warn("Trainee or user is null for username: {}", username);
            throw new IllegalArgumentException("Invalid trainee or user for username: " + username);
        }

    }
}

