package com.project.project.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JwtRequest {

    @NotBlank(message = "Это поле обязательное")
    @Size(min = 3,max = 50, message = "Имя пользователя должно содержать от 3 до 50 символов")
    private String username;

    @NotBlank(message = "Это поле обязательное")
    @Size(min=3,max = 100, message = "Пароль должен содержать от 6 до 100 символов")
    private String password;

}
