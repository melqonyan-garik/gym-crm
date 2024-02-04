package com.epam.dto.trainer;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class TrainerWithTraining {
    @NotNull
    private String username;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate periodFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate periodTo;
    private String traineeName;

}
