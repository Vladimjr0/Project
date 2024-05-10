package com.project.project.controllers;

import com.project.project.dtos.RegistrationUserDto;
import com.project.project.dtos.UserDto;
import com.project.project.dtos.UserUpdateDto;
import com.project.project.services.AuthService;
import com.project.project.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//TODO прописать для каждого метода @ApiResponse
@Tag(name = "Контроллер для взаимодействия с пользователями")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @Operation(summary = "Метод для просмотра списка всех пользователей")
    @GetMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Метод для просмотра информации об одном пользователе по ID")
    @GetMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "Метод для удаления пользователя по id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Метод для обновления информации о пользователе")
    @PutMapping("/{id}")
    @PreAuthorize("authenticated")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserUpdateDto userUpdateDto, @PathVariable Long id){
        return ResponseEntity.ok(userService.updateUser(userUpdateDto, id));
    }

    @Operation(summary = "Метод для регистрации нового пользователя")
    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public ResponseEntity<UserDto> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.createNewUser(registrationUserDto)) ;
    }

//TODO конвертация обычного пользователя в продавца    public ResponseEntity<?>

}
