package com.epam.storage;

import com.epam.dao.TrainerDao;
import com.epam.dto.TraineeJsonDto;
import com.epam.dto.TrainerJsonDto;
import com.epam.facade.Facade;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Component
@Slf4j
@Transactional
public class StorageBean {
    private final Resource traineeResource;
    private final Resource trainerResource;
    private final Facade facade;
    private final ObjectMapper objectMapper;
    private final TrainingTypeInitializer trainingTypeInitializer;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public StorageBean(@Value("${trainee.storage.data.file.path}") Resource traineeResource,
                       @Value("${trainer.storage.data.file.path}") Resource trainerResource,
                       Facade facade, ObjectMapper objectMapper, TrainerDao trainerDao, TrainingTypeInitializer trainingTypeInitializer) {
        this.traineeResource = traineeResource;
        this.trainerResource = trainerResource;
        this.facade = facade;
        this.objectMapper = objectMapper;
        this.trainingTypeInitializer = trainingTypeInitializer;

    }

    public void initializeStorage() {
        initializeTrainee();
        log.info("Trainee Storage initialization completed successfully.");

        initializeTrainer();
        log.info("Trainer Storage initialization completed successfully.");

        trainingTypeInitializer.initializeTrainingTypes();
        log.info("Training type initialization completed successfully.");
    }

    private void initializeTrainer() {
        String content = getTrainerFileContent();
        TrainerJsonDto[] trainerData;
        try {
            trainerData = objectMapper.readValue(content, TrainerJsonDto[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Arrays.stream(trainerData)
                .forEach(facade::saveTrainer);
    }

    private void initializeTrainee() {
        String content = getTraineeFileContent();
        TraineeJsonDto[] traineeData;
        try {
            traineeData = objectMapper.readValue(content, TraineeJsonDto[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Arrays.stream(traineeData)
                .forEach(facade::saveTraineeFromTraineeJsonData);
    }

    @SneakyThrows
    private String getTraineeFileContent() {
        return new String(StorageBean.class.getClassLoader().getResourceAsStream(traineeResource.getFilename()).readAllBytes());
    }

    @SneakyThrows
    private String getTrainerFileContent() {
        return new String(StorageBean.class.getClassLoader().getResourceAsStream(trainerResource.getFilename()).readAllBytes());
    }
}

