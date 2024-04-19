package com.project.project.services;

import com.project.project.models.Role;
import com.project.project.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Роль не найдена"));
    }
}
