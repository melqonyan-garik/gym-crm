package com.epam.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
public class User {

    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isActive;

}