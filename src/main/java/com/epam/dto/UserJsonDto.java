package com.epam.dto;

import lombok.Data;

@Data
public class UserJsonDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isActive;

}