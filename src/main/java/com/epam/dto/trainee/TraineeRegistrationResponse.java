package com.epam.dto.trainee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TraineeRegistrationResponse {
    private String username;
    private String password;
}
