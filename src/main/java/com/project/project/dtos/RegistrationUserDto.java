package com.project.project.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationUserDto {

    @NotBlank(message = "Это поле обязательно")
    @Size(min = 3,max = 50, message = "Имя пользователя должно содержать от 3 до 50 символов")
    private String username;

    @NotBlank(message = "Это поле обязательно")
    @Email
    private String email;

    @NotBlank(message = "Это поле обязательно")
    @Size(min = 3,max = 50, message = "Пароль должен содержать от 3 до 50 символов")
    private String password;

    @NotBlank(message = "Это поле обязательно")
    @Size(min = 3,max = 50, message = "Пароль должен содержать от 3 до 50 символов")
    private String confirmPassword;

}
