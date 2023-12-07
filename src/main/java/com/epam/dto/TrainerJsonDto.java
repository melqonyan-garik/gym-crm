package com.epam.dto;

import lombok.Data;

import java.util.List;

@Data
public class TrainerJsonDto {
    private UserJsonDto user;
    private List<TrainingJsonDto> trainings;
}
