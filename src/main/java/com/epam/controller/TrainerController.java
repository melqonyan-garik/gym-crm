package com.epam.controller;

import com.epam.dto.trainer.*;
import com.epam.mappers.TrainerMapper;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.model.TrainingType;
import com.epam.model.User;
import com.epam.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trainer")
@RequiredArgsConstructor
public class TrainerController {
    private final TrainerService trainerService;
    private final TrainerMapper mapper;

    @PostMapping
    public ResponseEntity<TrainerRegistrationResponse> registerTrainer(@RequestBody @Valid TrainerRegistrationRequest request) {
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
    @Transactional
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
    @Transactional
    public ResponseEntity<List<TrainerTrainingResponse>> getTrainerTrainings(@Valid TrainerWithTraining trainerWithTraining) {
        List<Training> trainings = trainerService.getTrainerTrainingsList(trainerWithTraining);

        return ResponseEntity.ok(mapper.trainingsToTrainerTrainingsResponse(trainings));
    }

    @PatchMapping("activate-deactivate")
    ResponseEntity<String> activateDeactivateTrainee(String username, boolean isActive) {
        Trainer trainer = trainerService.getTrainerByUsername(username).orElseThrow();

        if (isActive) {
            trainerService.activateTrainer(trainer.getId());
            return ResponseEntity.ok().build();
        }
        trainerService.deactivateTrainer(trainer.getId());
        return ResponseEntity.ok().build();
    }
}
