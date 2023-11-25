package com.epam.storage;

import com.epam.dto.TraineeJsonDto;
import com.epam.model.Trainee;
import com.epam.model.User;
import com.epam.service.impl.TraineeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

@Component
@Slf4j
@Transactional
public class StorageBean {
    private final Resource resource;
    private final TraineeServiceImpl traineeServiceImpl;
    private final ObjectMapper objectMapper;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public StorageBean(@Value("${storage.data.file.path}") Resource resource, TraineeServiceImpl traineeServiceImpl, ObjectMapper objectMapper) {
        this.resource = resource;
        this.traineeServiceImpl = traineeServiceImpl;
        this.objectMapper = objectMapper;
    }

    public void initializeStorage() {

        String content = getFileContent();
        TraineeJsonDto[] traineeData;
        try {
            traineeData = objectMapper.readValue(content, TraineeJsonDto[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Arrays.stream(traineeData)
                .forEach(this::saveTrainee);
       log. info("Storage initialization completed successfully.");

    }

    private void saveTrainee(TraineeJsonDto f) {
        Trainee trainee = new Trainee();
        trainee.setAddress(f.getAddress());
        trainee.setDateOfBirth(f.getDateOfBirth());
        User user = new User();
        user.setFirstName(f.getUser().getFirstName());
        user.setLastName(f.getUser().getLastName());
        user.setUsername(f.getUser().getUsername());
        user.setPassword(f.getUser().getPassword());
        user.setActive(f.getUser().isActive());
        trainee.setUser(user);
        entityManager.persist(user);
        entityManager.persist(trainee);
    }

    private String getFileContent() {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}

