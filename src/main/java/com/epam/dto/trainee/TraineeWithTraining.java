package com.epam.dto.trainee;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class TraineeWithTraining {
    @NotNull(message = "username cannot be null.")
    private String username;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate periodFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate periodTo;
    private String trainerName;
    private String trainingType;
}
