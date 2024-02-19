package com.project.project.dtos;

import com.project.project.models.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String userName;
    private String email;
}
