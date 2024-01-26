package com.epam.dto.trainee;

import com.epam.dto.trainer.TrainerProfile;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class TraineeUpdateResponseDto {
    private String username;
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    private String address;
    private boolean isActive;
    private List<TrainerProfile> trainers;
}
