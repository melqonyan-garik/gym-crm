package com.epam.dto.training;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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
