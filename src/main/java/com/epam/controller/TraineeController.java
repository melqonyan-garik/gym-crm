package com.epam.controller;

import com.epam.dto.trainee.*;
import com.epam.dto.trainer.TrainerProfile;
import com.epam.mappers.TraineeMapper;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.service.TraineeService;
import com.epam.service.TrainerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trainee")
@RequiredArgsConstructor
@Slf4j
public class TraineeController {
    private final TraineeMapper mapper;
    private final TraineeService traineeService;
    private final TrainerService trainerService;

    @GetMapping
    public ResponseEntity<TraineeProfileResponse> getTraineeProfile(@RequestParam String username) {
        Optional<Trainee> traineeOptional = traineeService.getTraineeByUsername(username);
        if (traineeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Trainee trainee = traineeOptional.get();
        TraineeProfileResponse profile = mapper.traineeToTraineeProfileResponse(trainee);

        return ResponseEntity.ok(profile);
    }

    @PutMapping
    public ResponseEntity<TraineeUpdateResponseDto> updateTraineeProfile(@RequestParam(name = "username") String username,
                                                                         @Valid @RequestBody TraineeUpdateRequestDto request) {
        Optional<Trainee> updatedTraineeOptional = traineeService.getTraineeByUsername(username);
        if (updatedTraineeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Trainee updatedTrainee = updatedTraineeOptional.get();
        mapper.updateEntity(updatedTrainee, request);

        Trainee trainee = traineeService.updateTrainee(updatedTrainee).get();
        TraineeUpdateResponseDto updateResponseDto = mapper.traineeToTraineeUpdateResponseDto(trainee);
        return ResponseEntity.ok(updateResponseDto);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTraineeProfile(@RequestParam String username) {
        boolean deleted = traineeService.deleteTraineeByUsername(username);

        if (deleted) {
            return ResponseEntity.ok("Trainee profile deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trainee not found");
        }
    }

    @GetMapping("trainers/unassigned")
    ResponseEntity<List<TrainerProfile>> getNotAssignedTrainers(@RequestParam(value = "username") String username) {
        List<Trainer> notAssignedTrainers = traineeService.getNotAssignedTrainers(username);
        List<TrainerProfile> trainerProfiles = mapper.TrainersToTrainerProfiles(notAssignedTrainers);
        return ResponseEntity.ok(trainerProfiles);
    }

    @PutMapping("/trainers/assignment")
    public ResponseEntity<List<TrainerProfile>> updateTraineesTrainerList(@RequestBody @Valid TraineeWithTrainersList traineeWithTrainers) {
        Optional<Trainee> traineeOptional = traineeService.getTraineeByUsername(traineeWithTrainers.getTraineeUsername());
        if (traineeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Trainee trainee = traineeOptional.get();
        List<Trainer> trainers = traineeWithTrainers.getTrainersList()
                .stream()
                .map(trainerUsername -> getTrainer(trainerUsername, trainee))
                .collect(Collectors.toList());
        trainee.getTrainers().addAll(trainers);

        traineeService.updateTrainee(trainee);
        return ResponseEntity.ok(mapper.TrainersToTrainerProfiles(trainers));
    }

    private Trainer getTrainer(String trainerUsername, Trainee trainee) {
        Optional<Trainer> trainerOptional = trainerService.getTrainerByUsername(trainerUsername);
        if (trainerOptional.isEmpty()) {
            throw new NoSuchElementException("there is no trainer with username :" + trainerUsername);
        }
        Trainer trainer = trainerOptional.get();
        trainer.getTrainees().add(trainee);
        return trainer;
    }

    @GetMapping("/trainings")
    public ResponseEntity<List<TraineeTrainingResponse>> getTraineeTrainings(@Valid TraineeWithTraining traineeWithTraining) {
        List<Training> trainings = traineeService.getTraineeTrainingsList(traineeWithTraining);

        return ResponseEntity.ok(mapper.trainingsToTraineeTrainingResponse(trainings));
    }

    @PatchMapping("/activate")
    ResponseEntity<String> activateTrainee(String username) {
        Trainee trainee = traineeService.getTraineeByUsername(username).orElseThrow();
        traineeService.activateTrainee(trainee.getId());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/deactivate")
    ResponseEntity<String> deactivateTrainee(String username) {
        Trainee trainee = traineeService.getTraineeByUsername(username).orElseThrow();
        traineeService.deactivateTrainee(trainee.getId());
        return ResponseEntity.ok().build();
    }
}
