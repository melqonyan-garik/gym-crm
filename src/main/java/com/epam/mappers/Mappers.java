package com.epam.mappers;

import com.epam.dto.TraineeJsonDto;
import com.epam.dto.TrainerJsonDto;
import com.epam.dto.TrainingJsonDto;
import com.epam.dto.UserJsonDto;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.model.User;

import java.util.List;

public class Mappers {
    public static Trainee convertTraineeJsonDtoToTrainee(TraineeJsonDto traineeJsonDto) {
        Trainee trainee = new Trainee();
        trainee.setAddress(traineeJsonDto.getAddress());
        trainee.setDateOfBirth(traineeJsonDto.getDateOfBirth());
        User user = convertUserJsonDtoToUser(traineeJsonDto.getUser());
        trainee.setUser(user);
        return trainee;
    }

    public static Trainer convertTrainerJsonDtoToTrainer(TrainerJsonDto trainerJsonDto) {
        Trainer trainer = new Trainer();
        List<Training> trainings = trainerJsonDto.getTrainings()
                .stream()
                .map(tr -> {
                    Training training = Mappers.convertTrainingJsonDtoToTraining(tr);
                    training.setTrainer(trainer);
                    return training;
                })
                .toList();
        trainer.setTrainings(trainings);

        User user = convertUserJsonDtoToUser(trainerJsonDto.getUser());
        trainer.setUser(user);
        return trainer;
    }

    public static User convertUserJsonDtoToUser(UserJsonDto userJsonDto) {
        User user = new User();
        user.setFirstName(userJsonDto.getFirstName());
        user.setLastName(userJsonDto.getLastName());
        user.setUsername(userJsonDto.getUsername());
        user.setPassword(userJsonDto.getPassword());
        user.setActive(userJsonDto.isActive());
        return user;
    }

    public static Training convertTrainingJsonDtoToTraining(TrainingJsonDto tr) {
        Training training = new Training();
        training.setTrainingName(tr.getTrainingName());
        training.setTrainingDate(tr.getTrainingDate());
        training.setTrainingDuration(tr.getTrainingDuration());
        training.setTrainingDuration(tr.getTrainingDuration());
        return training;
    }

}
