package com.project.project.services;

import com.project.project.dtos.RegistrationUserDto;
import com.project.project.dtos.UserDto;
import com.project.project.dtos.UserUpdateDto;
import com.project.project.mapper.ApiMapper;
import com.project.project.models.User;
import com.project.project.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public UserDto getUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));
        return ApiMapper.INSTANCE.userToUserDto(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException(("Пользователь не найден")));
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getUserPassword(),
                user.getRoles().stream().
                        map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()));

    }

    public User createNewUser(RegistrationUserDto registrationUserDto) {
        User user = new User();
        user.setUserName(registrationUserDto.getUserName());
        user.setEmail(registrationUserDto.getEmail());
        user.setUserPassword(passwordEncoder.encode(registrationUserDto.getUserPassword()));
        user.setRoles(List.of(roleService.getUserRole()));
        return userRepository.save(user);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(ApiMapper.INSTANCE::userToUserDto)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserDto updateUser(UserUpdateDto userUpdateDto, Long id) {

        User user = userRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));
        user.setUserName(userUpdateDto.getUserName());
        user.setEmail(userUpdateDto.getEmail());
        user.setUserPassword(userUpdateDto.getUserPassword());
        userRepository.save(user);

        return new UserDto(user.getId(), user.getUserName(), user.getEmail());
    }
}
