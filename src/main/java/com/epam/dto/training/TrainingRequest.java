package com.epam.dto.training;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TrainingRequest {

    @NotNull
    private String traineeUsername;
    @NotNull
    private String trainerUsername;
    @NotNull
    private String trainingName;
    @NotNull
    private String trainingDate;
    @NotNull
    private String trainingDuration;
}
