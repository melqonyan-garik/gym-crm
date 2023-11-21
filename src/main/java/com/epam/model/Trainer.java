package com.epam.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
public class Trainer {
    private Integer id;
    private TrainingType specialization;
    private User user;
    private List<Trainee> trainees;
    private List<Training> trainings;

}
