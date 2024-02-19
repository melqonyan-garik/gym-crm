package com.epam.dto.training;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainingRequest {

    @NotNull(message = "traineeUsername cannot be null.")
    private String traineeUsername;
    @NotNull(message = "trainerUsername cannot be null.")
    private String trainerUsername;
    @NotNull(message = "trainingName cannot be null.")
    private String trainingName;
    @NotNull(message = "trainingDate cannot be null.")
    private String trainingDate;
    @NotNull(message = "trainingDuration cannot be null.")
    private String trainingDuration;
}
