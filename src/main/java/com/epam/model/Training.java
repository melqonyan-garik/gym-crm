package com.epam.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
public class Training {
    private Integer id;
    private Trainee trainee;
    private Trainer trainer;
    private String trainingName;
    private TrainingType trainingType;
    private LocalDate trainingDate;
    private int trainingDuration;
}
