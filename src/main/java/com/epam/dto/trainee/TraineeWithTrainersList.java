package com.epam.dto.trainee;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TraineeWithTrainersList {
    @NotNull(message = "traineeUsername cannot be null.")
    private String traineeUsername;
    @NotEmpty(message = "trainersList cannot be empty")
    private List<String> trainersList;
}
