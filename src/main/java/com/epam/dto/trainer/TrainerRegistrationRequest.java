package com.epam.dto.trainer;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerRegistrationRequest {
    @NotNull(message = "firstname cannot be null.")
    private String firstname;
    @NotNull(message = "lastname cannot be null.")
    private String lastname;
    @NotNull(message = "specialization cannot be null.")
    private String specialization;
}
