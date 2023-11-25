package com.epam.service.impl;

import com.epam.dao.TrainingDao;
import com.epam.model.Training;
import com.epam.service.TrainerService;
import com.epam.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {
    @Autowired
    private TrainingDao trainingDao;

    public void createTraining(Training training) {
        trainingDao.save(training);
    }

    public void updateTraining(Training training) {
        trainingDao.update(training.getId(), training);
    }

    public void deleteTraining(Integer trainingId) {
        trainingDao.delete(trainingId);
    }

    public Training getTrainingById(Integer trainingId) {
        return trainingDao.findById(trainingId);
    }

    public List<Training> getAllTrainings() {
        return trainingDao.findAll()
                .stream()
                .toList();
    }
}