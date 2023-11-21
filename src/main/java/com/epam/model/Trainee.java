package com.epam.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class Trainee {

    private Integer id;
    private LocalDate dateOfBirth;
    private String address;
    private User user;
    private List<Trainer> trainers;
    private List<Training> trainings;

}
