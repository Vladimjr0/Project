package com.project.project.controllers;

import com.project.project.dtos.UserUpdateDto;
import com.project.project.models.User;
import com.project.project.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Контроллер для взаимодействия с пользователями")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    //TODO не передавать User а DTO
    @Operation(summary = "Метод для просмотра списка всех пользователей")
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Метод для удаления пользователя по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Метод для обновления информации о пользователе")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDto userUpdateDto, @PathVariable Long id){
        return ResponseEntity.ok(userService.updateUser(userUpdateDto, id));
    }

}
