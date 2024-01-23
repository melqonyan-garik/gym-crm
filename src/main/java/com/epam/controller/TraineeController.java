package com.epam.controller;

import com.epam.dto.trainee.*;
import com.epam.dto.trainer.TrainerProfile;
import com.epam.exceptions.OperationFailureException;
import com.epam.mappers.TraineeMapper;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.service.TraineeService;
import com.epam.service.TrainerService;
import com.epam.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
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

    @PostMapping
    public ResponseEntity<TraineeRegistrationResponse> registerTrainee(@RequestBody @Valid TraineeRegistrationRequest request) {
        String username = UserUtils.getBaseUsername(request.getFirstname(), request.getLastname());
        Optional<Trainer> trainerOptional = trainerService.getTrainerByUsername(username);
        if (trainerOptional.isPresent()) {
            ResponseEntity.badRequest().body("Not possible to register as a trainer and trainee both");
        }

        Trainee trainee = mapper.traineeRegistrationRequestToTrainee(request);
        Trainee createdTrainee = traineeService.createTrainee(trainee);
        TraineeRegistrationResponse response = new TraineeRegistrationResponse(
                createdTrainee.getUser().getUsername(),
                createdTrainee.getUser().getPassword());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Transactional
    public ResponseEntity<TraineeProfileResponse> getTraineeProfile(@RequestParam String username) {
        Optional<Trainee> traineeOptional = traineeService.getTraineeByUsername(username);
        if (traineeOptional.isEmpty()) {
            ResponseEntity.notFound();
        }
        Trainee trainee = traineeOptional.get();
        TraineeProfileResponse profile = mapper.traineeToTraineeProfileResponse(trainee);

        return ResponseEntity.ok(profile);
    }

    @PutMapping
    @Transactional
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
        try {
            boolean deleted = traineeService.deleteTraineeByUsername(username);

            if (deleted) {
                return ResponseEntity.ok("Trainee profile deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trainee not found");
            }
        } catch (DataAccessException ex) {
            log.error("Error deleting trainee profile", ex);
            throw new OperationFailureException("Error deleting trainee profile. Please try again later.");
        }
    }

    @GetMapping("get-not-assigned-trainers")
    ResponseEntity<List<TrainerProfile>> getNotAssignedTrainers(@RequestParam(value = "username") String username) {
        List<Trainer> notAssignedTrainers = traineeService.getNotAssignedTrainers(username);
        List<TrainerProfile> trainerProfiles = mapper.TrainersToTrainerProfiles(notAssignedTrainers);
        return ResponseEntity.ok(trainerProfiles);
    }

    @PutMapping("update-by-trainers")
    @Transactional
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
    @Transactional
    public ResponseEntity<List<TraineeTrainingResponse>> getTraineeTrainings(@Valid TraineeWithTraining traineeWithTraining) {
        List<Training> trainings = traineeService.getTraineeTrainingsList(traineeWithTraining);

        return ResponseEntity.ok(mapper.trainingsToTraineeTrainingResponse(trainings));
    }

    @PatchMapping("activate-deactivate")
    ResponseEntity<String> activateDeactivateTrainee(String username, boolean isActive) {
        Trainee trainee = traineeService.getTraineeByUsername(username).orElseThrow();

        if (isActive) {
            traineeService.activateTrainee(trainee.getId());
            return ResponseEntity.ok().build();
        }
        traineeService.deactivateTrainee(trainee.getId());
        return ResponseEntity.ok().build();
    }
}
