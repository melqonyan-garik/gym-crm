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
        trainingDao.save(TrainingDao.TRAINING, training.getId(), training);
    }

    public void updateTraining(Training training) {
        trainingDao.update(TrainingDao.TRAINING, training.getId(), training);
    }

    public void deleteTraining(Integer trainingId) {
        trainingDao.delete(TrainingDao.TRAINING,trainingId);
    }

    public Training getTrainingById(Integer trainingId) {
        return trainingDao.findById(TrainingDao.TRAINING,trainingId);
    }

    public List<Training> getAllTrainings() {
        return trainingDao.findAll(TrainingDao.TRAINING)
                .values()
                .stream()
                .toList();
    }
}