package com.epam.dto.trainer;

import com.epam.dto.trainee.TraineeProfile;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class TrainerProfileResponse {
    private String firstname;
    private String lastname;
    private String specialization;
    private boolean isActive;
    private List<TraineeProfile> trainees;
}
