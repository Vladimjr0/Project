package com.project.project.dtos;


import lombok.Data;

@Data
public class RegistrationUserDto {

    private String username;
    private String email;
    private String password;
    private String confirmPassword;

}
