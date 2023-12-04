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

@Service
@Slf4j
public class TrainerServiceImpl implements TrainerService {
    @Autowired
    private TrainerDao trainerDao;
    @Autowired
    private UserUtils userUtils;


    public void createTrainer(Trainer trainer) {
        String username = userUtils.generateUsername(trainer.getUser());
        String password = UserUtils.generateRandomPassword();


        trainer.getUser().setUsername(username);
        trainer.getUser().setPassword(password);
        trainerDao.save(trainer);
    }

    public boolean updateTrainer(Trainer trainer) {
        areUsernameAndPasswordMatching(trainer.getUser().getUsername(), trainer.getUser().getPassword());
        return trainerDao.update(trainer.getId(), trainer);
    }

    public void deleteTrainer(Integer trainerId) {
        Trainer trainer = getTrainerById(trainerId);
        areUsernameAndPasswordMatching(trainer.getUser().getUsername(), trainer.getUser().getPassword());
        trainerDao.delete(trainerId);
    }

    public Trainer getTrainerById(Integer trainerId) {
        Trainer trainer = trainerDao.findById(trainerId);
        areUsernameAndPasswordMatching(trainer.getUser().getUsername(), trainer.getUser().getPassword());
        return trainer;
    }

    public List<Trainer> getAllTrainer() {
        return trainerDao.findAll()
                .stream()
                .toList();
    }

    public boolean changePassword(Integer trainerId, String currentPassword, String newPassword) {
        Trainer trainer = trainerDao.findById(trainerId);
        areUsernameAndPasswordMatching(trainer.getUser().getUsername(), trainer.getUser().getPassword());

        if (currentPassword.equals(trainer.getUser().getPassword())) {
            trainer.getUser().setPassword(newPassword);
            trainerDao.save(trainer);
            return true;
        }
        return false;
    }

    public void activateDeactivateTrainer(Integer trainerId, boolean activate) {
        Trainer trainer = trainerDao.findById(trainerId);
        User user = trainer.getUser();
        areUsernameAndPasswordMatching(user.getUsername(), user.getPassword());
        user.setActive(activate);
        trainerDao.save(trainer);

    }

    public boolean areUsernameAndPasswordMatching(String username, String password) {
        Assert.notNull(username, "Username must not be null");
        Assert.notNull(password, "Password must not be null");
        Trainer trainer = trainerDao.findByUsername(username);

        if (trainer != null && trainer.getUser() != null) {
            return trainer.getUser().getPassword().equals(password);
        } else {
            log.warn("Trainer or user is null for username: {}", username);
            throw new IllegalArgumentException("Invalid trainer or user for username: " + username);
        }
    }
}
