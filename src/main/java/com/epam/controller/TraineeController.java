package com.epam.controller;

import com.epam.dto.TraineeRegistrationRequest;
import com.epam.dto.TraineeRegistrationResponse;
import com.epam.model.Trainee;
import com.epam.model.User;
import com.epam.service.TraineeService;
import com.epam.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainee")
@RequiredArgsConstructor
public class TraineeController {
    private final UserUtils userUtils;
    private final TraineeService traineeService;

    @PostMapping
    public ResponseEntity<TraineeRegistrationResponse> registerTrainee(@RequestBody @Validated TraineeRegistrationRequest request) {
        Trainee trainee = new Trainee();
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        String username = userUtils.generateUsername(user);
        user.setUsername(username);
        String password = UserUtils.generateRandomPassword();
        user.setPassword(password);
        trainee.setAddress(request.getAddress());
        trainee.setDateOfBirth(request.getDateOfBirth());
        Trainee createdTrainee = traineeService.createTrainee(trainee);
        TraineeRegistrationResponse response = new TraineeRegistrationResponse(createdTrainee.getUser().getUsername(), createdTrainee.getUser().getPassword());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public String registerTrainee() {
        return "Hello World";
    }
}
