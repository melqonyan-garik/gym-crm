package com.epam.controller;

import com.epam.dto.trainee.TraineeRegistrationRequest;
import com.epam.dto.trainee.TraineeRegistrationResponse;
import com.epam.dto.trainer.TrainerRegistrationRequest;
import com.epam.dto.trainer.TrainerRegistrationResponse;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.service.AuthenticationService;
import com.epam.service.LoginAttemptService;
import com.epam.service.TraineeService;
import com.epam.service.TrainerService;
import com.epam.utils.UserUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final TrainerService trainerService;
    private final TraineeService traineeService;
    private final LoginAttemptService loginAttemptService;

    @PostMapping("/register-trainee")
    public ResponseEntity<TraineeRegistrationResponse> registerTrainee(@RequestBody @Valid TraineeRegistrationRequest request) {
        String username = UserUtils.getBaseUsername(request.getFirstname(), request.getLastname());
        Optional<Trainer> trainerOptional = trainerService.getTrainerByUsername(username);
        if (trainerOptional.isPresent()) {
            throw new IllegalArgumentException("Not possible to register as a trainer and trainee both");
        }
        return ResponseEntity.ok(authenticationService.registerTraineeUser(request));
    }

    @PostMapping("/register-trainer")
    public ResponseEntity<TrainerRegistrationResponse> registerTrainer(@RequestBody @Valid TrainerRegistrationRequest request) {
        String username = UserUtils.getBaseUsername(request.getFirstname(), request.getLastname());
        Optional<Trainee> traineeOptional = traineeService.getTraineeByUsername(username);
        if (traineeOptional.isPresent()) {
            throw new IllegalArgumentException("Not possible to register as a trainer and trainee both");
        }

        return ResponseEntity.ok(authenticationService.registerTrainerUser(request));

    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        try {
            String authenticate = authenticationService.authenticate(username, password);
            return ResponseEntity.ok(authenticate);
        } catch (LockedException e) {
            String minutesToWait = "5"; //get the value from your LockedUserCacheService
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Your account has been locked due to too many unsuccessful login attempts. " +
                          "Please wait for " + minutesToWait + " minutes and try again.");
        } catch (Exception e) {
            loginAttemptService.loginFailed(username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Failed to authenticate. Please verify your credentials and try again.");
        }
    }

}
