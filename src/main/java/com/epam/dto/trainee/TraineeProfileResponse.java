package com.epam.dto.trainee;

import com.epam.dto.trainer.TrainerProfile;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TraineeProfileResponse {
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    private String address;
    private boolean isActive;
    private List<TrainerProfile> trainers;

}
