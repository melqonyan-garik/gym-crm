package com.epam.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class TrainingType {
    private Integer id;
    private String trainingTypeName;
    private List<Trainer> trainers;
    private List<Training> trainings;
}
