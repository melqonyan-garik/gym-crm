package com.epam.service;

import com.epam.dto.trainee.TraineeRegistrationRequest;
import com.epam.dto.trainee.TraineeRegistrationResponse;
import com.epam.dto.trainer.TrainerRegistrationRequest;
import com.epam.dto.trainer.TrainerRegistrationResponse;
import com.epam.mappers.TraineeMapper;
import com.epam.model.*;
import com.epam.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final TraineeMapper mapper;
    private final UserUtils userUtils;
    private final UserRepository userRepository;
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final LoginAttemptService loginAttemptService;
    private final TokenRepository tokenRepository;

    public TraineeRegistrationResponse registerTraineeUser(TraineeRegistrationRequest request) {
        Trainee trainee = mapper.traineeRegistrationRequestToTrainee(request);
        User user = trainee.getUser();
        String username = userUtils.generateUsername(user.getFirstname(), user.getLastname());
        user.setUsername(username);
        String password = UserUtils.generateRandomPassword();
        user.setPassword(passwordEncoder.encode(password));
        traineeService.createTrainee(trainee);
        String jwtToken = jwtService.generateToken(user);
        Token token = new Token();
        token.setToken(jwtToken);
        token.setRevoked(false);
        token.setExpired(false);
        token.setUser(user);
        tokenRepository.save(token);
        return new TraineeRegistrationResponse(username, password, jwtToken);
    }

    public TrainerRegistrationResponse registerTrainerUser(TrainerRegistrationRequest request) {
        Trainer trainer = new Trainer();
        User user = new User();
        String username = userUtils.generateUsername(request.getFirstname(), request.getLastname());
        String password = UserUtils.generateRandomPassword();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        trainer.setUser(user);
        TrainingType trainingType = new TrainingType(request.getSpecialization(), List.of(trainer));
        trainer.setSpecialization(trainingType);
        trainerService.createTrainer(trainer);
        String jwtToken = jwtService.generateToken(user);
        Token token = new Token();
        token.setToken(jwtToken);
        token.setRevoked(false);
        token.setExpired(false);
        token.setUser(user);
        tokenRepository.save(token);
        return new TrainerRegistrationResponse(username, password, jwtToken);
    }

    public String authenticate(String username, String password) {
        if (loginAttemptService.isBlocked(username)) {
            throw new LockedException("User account is locked");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        User user = userRepository.findByUsername(username)
                .orElseThrow();
        return jwtService.generateToken(user);
    }

}
