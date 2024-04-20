package com.project.project.services;

import com.project.project.dtos.CategoryRequestDto;
import com.project.project.entities.Category;
import com.project.project.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryRequestDto create(CategoryRequestDto categoryRequestDto) {
        if (categoryRepository.existsByName(categoryRequestDto.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Такая категория уже существует");
        }
        Category category = new Category();
        category.setName(categoryRequestDto.getName());

        categoryRepository.save(category);

        return categoryRequestDto;

    }

    @Transactional
    public CategoryRequestDto update(CategoryRequestDto categoryRequestDto, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Такой категории не существует"));

        category.setName(categoryRequestDto.getName());
        categoryRepository.save(category);

        return categoryRequestDto;
    }

    @Transactional
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Category> getAllCategory() {
        return categoryRepository.findAll(Sort.by("name"));
    }

}
