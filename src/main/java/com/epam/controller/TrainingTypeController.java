package com.epam.controller;

import com.epam.dao.TrainingTypeDao;
import com.epam.dto.trainingType.TrainingTypeResponse;
import com.epam.model.TrainingType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("training-types")
public class TrainingTypeController {

    private final TrainingTypeDao trainingTypeDao;

    @GetMapping
    ResponseEntity<List<TrainingTypeResponse>> getTrainingTypes() {
        List<TrainingType> trainingTypes = trainingTypeDao.getAllTrainingTypes();
        List<TrainingTypeResponse> trainingTypeResponses = trainingTypes.stream()
                .map(trainingType -> new TrainingTypeResponse(trainingType.getTrainingTypeName(), trainingType.getId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(trainingTypeResponses);
    }

}
