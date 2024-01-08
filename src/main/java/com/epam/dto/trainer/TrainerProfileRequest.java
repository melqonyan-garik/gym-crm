package com.epam.dto.trainer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerProfileRequest {
    private String username;
    private String firstName;
    private String lastName;
    private boolean active;
    private String specialization;

}