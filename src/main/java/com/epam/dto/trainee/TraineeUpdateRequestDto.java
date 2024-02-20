package com.epam.dto.trainee;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TraineeUpdateRequestDto {

    @NotNull(message = "firstname cannot be null.")
    private String firstname;
    @NotNull(message = "lastname cannot be null.")
    private String lastname;
    private String dateOfBirth;
    private String address;
    private boolean active;


}