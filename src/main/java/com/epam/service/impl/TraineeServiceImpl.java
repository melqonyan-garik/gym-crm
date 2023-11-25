package com.epam.service.impl;

import com.epam.dao.TraineeDao;
import com.epam.model.Trainee;
import com.epam.service.TraineeService;
import com.epam.utils.PasswordGeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TraineeServiceImpl implements TraineeService {
    @Autowired
    private TraineeDao traineeDAO;

    public void createTrainee(Trainee trainee) {
        String username = generateTraineeUsername(trainee);
        String password = PasswordGeneratorUtils.generateRandomPassword();
        if (trainee.getUser() != null) {
            trainee.getUser().setUsername(username);
            trainee.getUser().setPassword(password);

        }
        traineeDAO.save(trainee);
    }

    public void updateTrainee(Trainee trainee) {
        traineeDAO.update(trainee.getId(), trainee);
    }

    public void deleteTrainee(Integer traineeId) {
        traineeDAO.delete(traineeId);
    }

    public Trainee getTraineeById(Integer traineeId) {
        return traineeDAO.findById(traineeId);
    }

    public List<Trainee> getAllTrainees() {
        return traineeDAO.findAll()
                .stream()
                .toList();
    }

    public String generateTraineeUsername(Trainee trainee) {
        if (trainee.getUser() == null) {
            return null;
        }
        String baseUsername = trainee.getUser().getFirstName() + "." + trainee.getUser().getLastName();
        String username = baseUsername;

        Set<String> existingUsernames = new HashSet<>();
        traineeDAO.findAll().forEach(existingTrainee -> {
            String existingName = existingTrainee.getUser().getFirstName() + "." + existingTrainee.getUser().getLastName();
            existingUsernames.add(existingName);
        });

        // Add serial number if the username already exists
        int serialNumber = 1;
        while (existingUsernames.contains(username)) {
            username = baseUsername + serialNumber;
            serialNumber++;
        }

        return username;
    }


}

