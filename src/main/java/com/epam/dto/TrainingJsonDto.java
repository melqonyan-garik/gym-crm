package com.epam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TrainingJsonDto {
    private Integer id;
    private TraineeJsonDto trainee;
    private TrainerJsonDto trainer;
    private String trainingName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate trainingDate;
    private Double trainingDuration;
}
