package com.project.project.controllers;

import com.project.project.dtos.CategoryRequestDto;
import com.project.project.repositories.CategoryRepository;
import com.project.project.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Контроллер для создания категорий товара")
@RequestMapping("/category")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    @Operation(summary = "Метод для создания категорий")
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping
    public ResponseEntity<CategoryRequestDto> create(@RequestBody CategoryRequestDto categoryRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(categoryRequestDto));
    }

    @Operation(summary = "Метод для изменения категории")
    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryRequestDto> update(@RequestBody CategoryRequestDto categoryRequestDto, @PathVariable Long id){
        return ResponseEntity.ok(categoryService.update(categoryRequestDto, id));
    }

    @Operation(summary = "Метод для удаления категории")
    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
