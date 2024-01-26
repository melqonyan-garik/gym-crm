package com.epam.dto.trainee;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class TraineeWithTrainersList {
    @NotNull
    private String traineeUsername;
    @NotEmpty
    private List<String> trainersList;
}
