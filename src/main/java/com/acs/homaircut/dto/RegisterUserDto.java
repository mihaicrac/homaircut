package com.acs.homaircut.dto;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
