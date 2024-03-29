package com.epam.mappers;

import com.epam.config.MapStructGlobalConfig;
import com.epam.dto.trainee.*;
import com.epam.dto.trainer.TrainerProfile;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(config = MapStructGlobalConfig.class)
public interface TraineeMapper {
    @Mapping(target = "user.firstname", source = "firstname")
    @Mapping(target = "user.lastname", source = "lastname")
    @Mapping(target = "dateOfBirth", source = "dateOfBirth", qualifiedByName = "stringToDate")
    Trainee traineeRegistrationRequestToTrainee(TraineeRegistrationRequest traineeRegister);

    @Mapping(target = "firstname", source = "user.firstname")
    @Mapping(target = "lastname", source = "user.lastname")
    @Mapping(target = "active", source = "user.active")
    TraineeProfileResponse traineeToTraineeProfileResponse(Trainee trainee);

    @Mapping(target = "specialization", source = "specialization.trainingTypeName")
    @Mapping(target = "firstname", source = "user.firstname")
    @Mapping(target = "lastname", source = "user.lastname")
    @Mapping(target = "username", source = "user.username")
    TrainerProfile TrainerToTrainerProfile(Trainer trainer);

    List<TrainerProfile> TrainersToTrainerProfiles(List<Trainer> trainers);

    @Mapping(target = "user.firstname", source = "firstname")
    @Mapping(target = "user.lastname", source = "lastname")
    @Mapping(target = "user.active", source = "active")
    @Mapping(target = "dateOfBirth", source = "dateOfBirth", qualifiedByName = "stringToDate")
    void updateEntity(@MappingTarget Trainee trainee, TraineeUpdateRequestDto updateDto);

    @Mapping(target = "firstname", source = "user.firstname")
    @Mapping(target = "lastname", source = "user.lastname")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "active", source = "user.active")
    TraineeUpdateResponseDto traineeToTraineeUpdateResponseDto(Trainee trainee);

    @Mapping(target = "trainingType", source = "trainingType.trainingTypeName")
    @Mapping(target = "trainerName", source = "trainer.user.username")
    TraineeTrainingResponse trainingToTraineeTrainingResponse(Training trainings);

    List<TraineeTrainingResponse> trainingsToTraineeTrainingResponse(List<Training> trainings);

    TraineeRegistrationResponse traineeToTraineeRegistrationResponse(User user);
    @Named("stringToDate")
    default LocalDate stringToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date != null ? LocalDate.parse(date, formatter) : null;
    }
}
