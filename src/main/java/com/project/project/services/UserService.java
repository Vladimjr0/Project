package com.project.project.services;

import com.project.project.exceptions.RegistrationUserDto;
import com.project.project.models.User;
import com.project.project.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private  RoleService roleService;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь не найден")));
        return new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getUserPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));

    }
    public User createNewUser(RegistrationUserDto registrationUserDto){
        User user = new User();
        user.setUserName(registrationUserDto.getUserName());
        user.setEmail(registrationUserDto.getEmail());
        user.setUserPassword(passwordEncoder.encode(registrationUserDto.getUserPassword()));
        user.setRoles(List.of(roleService.getUserRole()));
        return userRepository.save(user);
    }
}
