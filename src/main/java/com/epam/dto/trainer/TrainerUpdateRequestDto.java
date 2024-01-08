package com.epam.dto.trainer;

import lombok.Data;

@Data
public class TrainerUpdateRequestDto {
    private String firstname;
    private String lastname;
    private String specialization;
    private boolean isActive;
}
