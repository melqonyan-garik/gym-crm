package com.epam.dto.trainer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TrainerRegistrationResponse {
    private String username;
    private String password;
}
