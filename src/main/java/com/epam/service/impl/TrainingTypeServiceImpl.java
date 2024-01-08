package com.epam.service.impl;

import com.epam.dao.TrainingTypeDao;
import com.epam.model.TrainingType;
import com.epam.service.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingTypeServiceImpl implements TrainingTypeService {
    private final TrainingTypeDao trainingTypeDao;
    @Override
    public List<TrainingType> getAllTrainingTypes() {
        return trainingTypeDao.getAllTrainingTypes();
    }
}
