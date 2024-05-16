package com.project.project.services;

import com.project.project.dtos.JwtRequest;
import com.project.project.dtos.JwtResponse;
import com.project.project.dtos.RegistrationUserDto;
import com.project.project.dtos.UserDto;
import com.project.project.entities.User;
import com.project.project.exceptions.AuthenticationException;
import com.project.project.exceptions.PasswordMismatchException;
import com.project.project.exceptions.UserAlreadyExistsException;
import com.project.project.mapper.ApiMapper;
import com.project.project.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;

    public JwtResponse createAuthToken(JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Отказано в доступе");
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return new  JwtResponse(token);
    }

    @Transactional
    public UserDto createNewUser(RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            throw new PasswordMismatchException("Пароли не совпадают");
        }
        if (userService.findByEmail(registrationUserDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Пользователь с такой почтой уже существует");
        }

        User user = userService.createNewUser(registrationUserDto);
        return ApiMapper.INSTANCE.userToUserDto(user);
    }
}
