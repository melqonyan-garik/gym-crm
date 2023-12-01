package com.epam.service.impl;

import com.epam.dao.TraineeDao;
import com.epam.model.Trainee;
import com.epam.model.User;
import com.epam.service.TraineeService;
import com.epam.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TraineeServiceImpl implements TraineeService {
    @Autowired
    private TraineeDao traineeDao;
    @Autowired
    private UserUtils userUtils;


    public void createTrainee(Trainee trainee) {
        String username = userUtils.generateUsername(trainee.getUser());
        String password = UserUtils.generateRandomPassword();
        if (trainee.getUser() != null) {
            trainee.getUser().setUsername(username);
            trainee.getUser().setPassword(password);

        }
        traineeDao.save(trainee);
    }

    public boolean updateTrainee(Trainee trainee) {
        checkUsernameAndPasswordMatching(trainee.getUser().getUsername(), trainee.getUser().getPassword());
        return traineeDao.update(trainee.getId(), trainee);
    }

    public void deleteTrainee(Integer traineeId) {
        getTraineeById(traineeId);
        traineeDao.delete(traineeId);
    }

    public Trainee getTraineeById(Integer traineeId) {
        Trainee trainee = traineeDao.findById(traineeId);
        checkUsernameAndPasswordMatching(trainee.getUser().getUsername(), trainee.getUser().getPassword());
        return trainee;
    }

    public Trainee getTraineeByUsername(String username) {
        Trainee trainee = traineeDao.findByUsername(username);
        checkUsernameAndPasswordMatching(trainee.getUser().getUsername(), trainee.getUser().getPassword());
        return trainee;
    }

    public List<Trainee> getAllTrainees() {
        return traineeDao.findAll()
                .stream()
                .toList();
    }

    public boolean changePassword(Integer traineeId, String currentPassword, String newPassword) {
        Trainee trainee = traineeDao.findById(traineeId);
        checkUsernameAndPasswordMatching(trainee.getUser().getUsername(), trainee.getUser().getPassword());

        if (currentPassword.equals(trainee.getUser().getPassword())) {
            trainee.getUser().setPassword(newPassword);
            traineeDao.save(trainee);
            return true;
        }
        return false;
    }

    public void activateDeactivateTrainee(Integer traineeId, boolean activate) {
        Trainee trainee = traineeDao.findById(traineeId);
        User user = trainee.getUser();
        checkUsernameAndPasswordMatching(user.getUsername(), user.getPassword());
        user.setActive(activate);
        traineeDao.save(trainee);

    }

    public void deleteTraineeByUsername(String username) {
        Trainee trainee = traineeDao.findByUsername(username);
        checkUsernameAndPasswordMatching(trainee.getUser().getUsername(), trainee.getUser().getPassword());
        traineeDao.deleteByUsername(username);
    }

    public void checkUsernameAndPasswordMatching(String username, String password) {
        Trainee trainee = traineeDao.findByUsername(username);
        if (trainee == null || !trainee.getUser().getPassword().equals(password)) {
            throw new NoSuchElementException();
        }
    }
}

