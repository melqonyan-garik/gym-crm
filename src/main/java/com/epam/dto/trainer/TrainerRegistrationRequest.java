package com.epam.dto.trainer;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerRegistrationRequest {
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private String specialization;
}
