package com.epam.service.impl;

import com.epam.dao.TrainingDao;
import com.epam.model.Training;
import com.epam.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingService {
    @Autowired
    private TrainingDao trainingDao;

    public Training createTraining(Training training) {
        return trainingDao.save(training);

    }

    public Optional<Training> updateTraining(Training training) {
        return trainingDao.update(training);
    }

    public Optional<Training> getTrainingById(Integer trainingId) {
        return trainingDao.findById(trainingId);
    }

    public List<Training> getAllTrainings() {
        return trainingDao.findAll()
                .stream()
                .toList();
    }

    public boolean deleteTraining(Integer trainingId) {
        return trainingDao.delete(trainingId);
    }
}