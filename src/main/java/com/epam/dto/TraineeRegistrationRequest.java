package com.epam.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TraineeRegistrationRequest {
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;

}
