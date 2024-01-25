package com.epam.dto.trainee;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class TraineeWithTraining {
    @NotNull
    private String username;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate periodFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate periodTo;
    private String trainerName;
    private String trainingType;
}
