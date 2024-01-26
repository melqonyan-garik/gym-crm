package com.epam.controller;

import com.epam.dto.trainer.*;
import com.epam.mappers.TrainerMapper;
import com.epam.model.*;
import com.epam.service.TraineeService;
import com.epam.service.TrainerService;
import com.epam.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trainer")
@RequiredArgsConstructor
@Slf4j
public class TrainerController {
    private final TrainerService trainerService;
    private final TrainerMapper mapper;
    private final TraineeService traineeService;

    @PostMapping
    public ResponseEntity<TrainerRegistrationResponse> registerTrainer(@RequestBody @Valid TrainerRegistrationRequest request) {
        String username = UserUtils.getBaseUsername(request.getFirstname(), request.getLastname());
        Optional<Trainee> traineeOptional = traineeService.getTraineeByUsername(username);
        if (traineeOptional.isPresent()) {
            ResponseEntity.badRequest().body("Not possible to register as a trainer and trainee both");
        }

        Trainer trainer = new Trainer();
        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        trainer.setUser(user);
        TrainingType trainingType = new TrainingType(request.getSpecialization(), List.of(trainer));
        trainer.setSpecialization(trainingType);


        Trainer createdTrainer = trainerService.createTrainer(trainer);
        TrainerRegistrationResponse response = new TrainerRegistrationResponse(createdTrainer.getUser().getUsername(), createdTrainer.getUser().getPassword());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<TrainerProfileResponse> getTrainerProfile(@RequestParam String username) {
        Optional<Trainer> trainerOptional = trainerService.getTrainerByUsername(username);
        if (trainerOptional.isEmpty()) {
            ResponseEntity.notFound();
        }
        Trainer trainer = trainerOptional.get();
        TrainerProfileResponse profileResponse = mapper.trainerToTrainerProfileResponse(trainer);
        return ResponseEntity.ok(profileResponse);
    }

    @PutMapping
    public ResponseEntity<TrainerUpdateResponseDto> updateTrainerProfile(@RequestParam(name = "username") String username, @RequestBody TrainerUpdateRequestDto request) {
        Optional<Trainer> updatedTrainerOptional = trainerService.getTrainerByUsername(username);
        if (updatedTrainerOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Trainer updatedTrainer = updatedTrainerOptional.get();
        mapper.updateEntity(updatedTrainer, request);
        TrainingType trainingType = new TrainingType(request.getSpecialization(), List.of(updatedTrainer));
        updatedTrainer.setSpecialization(trainingType);
        Trainer trainer = trainerService.updateTrainer(updatedTrainer).get();

        TrainerUpdateResponseDto updateResponseDto = mapper.trainerToTrainerUpdateResponseDto(trainer);
        return ResponseEntity.ok(updateResponseDto);
    }

    @GetMapping("/trainings")
    public ResponseEntity<List<TrainerTrainingResponse>> getTrainerTrainings(@Valid TrainerWithTraining trainerWithTraining) {
        List<Training> trainings = trainerService.getTrainerTrainingsList(trainerWithTraining);

        return ResponseEntity.ok(mapper.trainingsToTrainerTrainingsResponse(trainings));
    }

    @PatchMapping("/activate")
    ResponseEntity<String> activateTrainee(String username) {
        Trainer trainer = trainerService.getTrainerByUsername(username).orElseThrow();
        trainerService.activateTrainer(trainer.getId());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/deactivate")
    ResponseEntity<String> deactivateTrainer(String username) {
        Trainer trainer = trainerService.getTrainerByUsername(username).orElseThrow();
        trainerService.deactivateTrainer(trainer.getId());
        return ResponseEntity.ok().build();
    }
}
