package com.project.project.dtos;

import com.project.project.models.Role;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.util.Collection;

public class RegistrationUserDto {
    private String userName;

    private String email;

    private String userPassword;
    private String confirmUserPassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getConfirmUserPassword() {
        return confirmUserPassword;
    }

    public void setConfirmUserPassword(String confirmUserPassword) {
        this.confirmUserPassword = confirmUserPassword;
    }
}
