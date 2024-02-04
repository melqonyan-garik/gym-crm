package com.epam.dto.trainee;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraineeRegistrationRequest {
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    private LocalDate dateOfBirth;
    private String address;

}
