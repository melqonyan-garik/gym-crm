package com.epam.dto.trainee;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraineeRegistrationRequest {
    @NotNull(message = "firstname cannot be null.")
    private String firstname;
    @NotNull(message = "lastname cannot be null.")
    private String lastname;
    private String dateOfBirth;
    private String address;

}
