package com.epam.service.impl;

import com.epam.dao.TrainerDao;
import com.epam.model.Trainer;
import com.epam.service.TrainerService;
import com.epam.utils.PasswordGeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    private TrainerDao trainerDao;

    public void createTrainer(Trainer trainer) {
        String username = generateTrainerUsername(trainer);
        String password = PasswordGeneratorUtils.generateRandomPassword();

        // Set username and password for the trainee
        trainer.getUser().setUsername(username);
        trainer.getUser().setPassword(password);
        trainerDao.save(trainer);
    }

    public void updateTrainer(Trainer trainer) {
        trainerDao.update(trainer.getId(), trainer);
    }

    public void deleteTrainer(Integer trainerId) {
        trainerDao.delete(trainerId);
    }

    public Trainer getTrainerById(Integer trainerId) {
        return trainerDao.findById(trainerId);
    }

    public List<Trainer> getAllTrainer() {
        return trainerDao.findAll()
                .stream()
                .toList();
    }

    public String generateTrainerUsername(Trainer trainee) {
        String baseUsername = trainee.getUser().getFirstName() + "." + trainee.getUser().getLastName();
        String username = baseUsername;

        Set<String> existingUsernames = new HashSet<>();
        trainerDao.findAll().forEach(existingTrainee -> {
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
