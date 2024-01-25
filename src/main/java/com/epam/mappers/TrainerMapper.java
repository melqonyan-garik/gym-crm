package com.epam.mappers;

import com.epam.config.MapStructGlobalConfig;
import com.epam.dto.trainer.TrainerProfileResponse;
import com.epam.dto.trainer.TrainerTrainingResponse;
import com.epam.dto.trainer.TrainerUpdateRequestDto;
import com.epam.dto.trainer.TrainerUpdateResponseDto;
import com.epam.model.Trainer;
import com.epam.model.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = MapStructGlobalConfig.class)
public interface TrainerMapper {
    @Mapping(target = "firstname", source = "user.firstname")
    @Mapping(target = "lastname", source = "user.lastname")
    @Mapping(target = "active", source = "user.active")
    @Mapping(target = "specialization", source = "specialization.trainingTypeName")
    TrainerProfileResponse trainerToTrainerProfileResponse(Trainer trainer);

    @Mapping(target = "user.firstname", source = "firstname")
    @Mapping(target = "user.lastname", source = "lastname")
    @Mapping(target = "user.active", source = "active")
    @Mapping(target = "specialization.trainingTypeName",source = "specialization",ignore = true)
    void updateEntity(@MappingTarget Trainer trainer, TrainerUpdateRequestDto request);

    @Mapping(target = "firstname", source = "user.firstname")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "lastname", source = "user.lastname")
    @Mapping(target = "active", source = "user.active")
    @Mapping(target = "specialization", source = "specialization.trainingTypeName")
    TrainerUpdateResponseDto trainerToTrainerUpdateResponseDto(Trainer trainer);
    @Mapping(target = "traineeName", source = "trainee.user.username")
    @Mapping(target = "trainingType", source = "trainingType.trainingTypeName")
    TrainerTrainingResponse trainingToTrainerTrainingResponse(Training trainings);
    List<TrainerTrainingResponse>trainingsToTrainerTrainingsResponse(List<Training> trainings);
}
