package com.epam.service.impl;

import com.epam.dao.TrainerDao;
import com.epam.model.Trainer;
import com.epam.model.User;
import com.epam.service.TrainerService;
import com.epam.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TrainerServiceImpl implements TrainerService {
    @Autowired
    private TrainerDao trainerDao;
    @Autowired
    private UserUtils userUtils;


    public void createTrainer(Trainer trainer) {
        String username = userUtils.generateUsername(trainer.getUser());
        String password = UserUtils.generateRandomPassword();

        // Set username and password for the trainee
        trainer.getUser().setUsername(username);
        trainer.getUser().setPassword(password);
        trainerDao.save(trainer);
    }

    public boolean updateTrainer(Trainer trainer) {
        checkUsernameAndPasswordMatching(trainer.getUser().getUsername(), trainer.getUser().getPassword());
        return trainerDao.update(trainer.getId(), trainer);
    }

    public void deleteTrainer(Integer trainerId) {
        Trainer trainer = getTrainerById(trainerId);
        checkUsernameAndPasswordMatching(trainer.getUser().getUsername(), trainer.getUser().getPassword());
        trainerDao.delete(trainerId);
    }

    public Trainer getTrainerById(Integer trainerId) {
        Trainer trainer = trainerDao.findById(trainerId);
        checkUsernameAndPasswordMatching(trainer.getUser().getUsername(), trainer.getUser().getPassword());
        return trainer;
    }

    public List<Trainer> getAllTrainer() {
        return trainerDao.findAll()
                .stream()
                .toList();
    }
    public boolean changePassword(Integer trainerId,String currentPassword, String newPassword) {
        Trainer trainer = trainerDao.findById(trainerId);
        checkUsernameAndPasswordMatching(trainer.getUser().getUsername(), trainer.getUser().getPassword());

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
        checkUsernameAndPasswordMatching(user.getUsername(), user.getPassword());
        user.setActive(activate);
        trainerDao.save(trainer);

    }

    public void checkUsernameAndPasswordMatching(String username, String password) {
        Trainer trainer = trainerDao.findByUsername(username);
        if (trainer == null || !trainer.getUser().getPassword().equals(password)) {
            throw new NoSuchElementException();
        }
    }
}
