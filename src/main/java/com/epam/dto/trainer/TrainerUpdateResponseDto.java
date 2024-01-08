package com.epam.dto.trainer;

import com.epam.dto.trainee.TraineeProfile;
import lombok.Data;

import java.util.List;
@Data
public class TrainerUpdateResponseDto {
    private String username;
    private String firstname;
    private String lastname;
    private String specialization;
    private boolean isActive;
    private List<TraineeProfile> trainees;
}
