package com.epam.service.impl;

import com.epam.dao.TrainerDao;
import com.epam.model.Trainer;
import com.epam.model.User;
import com.epam.service.TrainerService;
import com.epam.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TrainerServiceImpl implements TrainerService {
    @Autowired
    private TrainerDao trainerDao;
    @Autowired
    private UserUtils userUtils;


    public Trainer createTrainer(Trainer trainer) {
        String username = userUtils.generateUsername(trainer.getUser());
        String password = UserUtils.generateRandomPassword();
        if (trainer.getUser() != null) {
            trainer.getUser().setUsername(username);
            trainer.getUser().setPassword(password);

        }
        return trainerDao.save(trainer);
    }

    public Optional<Trainer> updateTrainer(Trainer trainer) {
        areUsernameAndPasswordMatching(trainer.getUser().getUsername(), trainer.getUser().getPassword());
        return trainerDao.update(trainer);
    }

    public Optional<Trainer> getTrainerById(Integer trainerId) {
        Optional<Trainer> optionalTrainer = trainerDao.findById(trainerId);
        if (optionalTrainer.isPresent()) {
            Trainer trainer = optionalTrainer.get();
            areUsernameAndPasswordMatching(trainer.getUser().getUsername(), trainer.getUser().getPassword());
        }
        return optionalTrainer;
    }

    public List<Trainer> getAllTrainer() {
        return trainerDao.findAll()
                .stream()
                .toList();
    }

    public boolean deleteTrainer(Integer trainerId) {
        Optional<Trainer> optionalTrainer = getTrainerById(trainerId);
        if (optionalTrainer.isPresent()) {
            Trainer trainer = optionalTrainer.get();
            boolean areUsernameAndPasswordMatching = areUsernameAndPasswordMatching(trainer.getUser().getUsername(), trainer.getUser().getPassword());
            if (areUsernameAndPasswordMatching) {
                return trainerDao.delete(trainerId);
            }
        }
        return false;

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

    public void activateDeactivateTrainer(Integer trainerId, boolean activate) {
        Optional<Trainer> optionalTrainer = trainerDao.findById(trainerId);
        if (optionalTrainer.isPresent()) {
            Trainer trainer = optionalTrainer.get();
            User user = trainer.getUser();
            areUsernameAndPasswordMatching(user.getUsername(), user.getPassword());
            user.setActive(activate);
            trainerDao.save(trainer);
        }

    }

    public boolean areUsernameAndPasswordMatching(String username, String password) {
        Assert.notNull(username, "Username must not be null");
        Assert.notNull(password, "Password must not be null");
        Optional<Trainer> optionalTrainer = trainerDao.findByUsername(username);

        if (optionalTrainer.isPresent() && optionalTrainer.get().getUser() != null) {
            return optionalTrainer.get().getUser().getPassword().equals(password);
        } else {
            log.warn("Trainer or user is null for username: {}", username);
            throw new IllegalArgumentException("Invalid trainer or user for username: " + username);
        }
    }
}
