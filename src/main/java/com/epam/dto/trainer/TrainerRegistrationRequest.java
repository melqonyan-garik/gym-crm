package com.epam.dto.trainer;

import lombok.Getter;

import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TrainerRegistrationRequest {
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private String specialization;
}
