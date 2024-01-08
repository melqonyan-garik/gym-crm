package com.epam.dto.trainee;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
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