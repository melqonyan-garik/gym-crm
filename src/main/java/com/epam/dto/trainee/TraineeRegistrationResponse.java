package com.epam.dto.trainee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraineeRegistrationResponse {
    private String username;
    private String password;
    private String token;

    public TraineeRegistrationResponse(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
