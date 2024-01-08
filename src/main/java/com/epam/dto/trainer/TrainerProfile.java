package com.epam.dto.trainer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainerProfile {
    private String username;
    private String firstname;
    private String lastname;
    private String specialization;

}
