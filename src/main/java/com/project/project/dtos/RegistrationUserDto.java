package com.project.project.dtos;


import lombok.Data;

@Data
public class RegistrationUserDto {

    private String userName;
    private String email;
    private String userPassword;
    private String confirmUserPassword;

}
