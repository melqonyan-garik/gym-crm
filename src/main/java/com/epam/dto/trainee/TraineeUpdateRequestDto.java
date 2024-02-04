package com.epam.dto.trainee;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TraineeUpdateRequestDto {

    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    private LocalDate dateOfBirth;
    private String address;
    private boolean active;


}