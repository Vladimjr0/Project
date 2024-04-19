package com.project.project.controllers;


import com.project.project.dtos.ProductAddDto;
import com.project.project.dtos.ProductsResponseDto;
import com.project.project.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Tag(name = "Контроллер для взаимодействия с товарами")
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Метод с помощью которого мы создаем новый товар")
    @PostMapping()
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ProductsResponseDto> addNewProduct(@RequestBody ProductAddDto productAddDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productAddDto));
    }

    @Operation(summary = "Метод, позволяющий посмотреть список всех товаров")
    @GetMapping()
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ProductsResponseDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(summary = "Метод для сортировки товаров по категориям")
    @GetMapping("/category/{categoryId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ProductsResponseDto>> sortProductByCategory(@PathVariable Long categoryId){
        return ResponseEntity.ok(productService.sortProductsByCategory(categoryId));
    }

    @Operation(summary = "Метод, удаляющий товар по id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> removeProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Метод, позволяющий посмотреть информацию о каком-то конкретном товаре по id")
    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ProductsResponseDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = "Метод для обновлении информации о товаре")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ProductsResponseDto> editProduct(@PathVariable Long id, @RequestBody ProductAddDto productAddDto) {
        return ResponseEntity.ok(productService.updateProduct(productAddDto, id));
    }

    @Operation(summary = "Метод для добавления категории товару")
    @PostMapping("/{id}/category/{categoryId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> addCategoryToProduct(@PathVariable Long id, @PathVariable Long categoryId){
        productService.addCategoryToProduct(id, categoryId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Метод для удаления категории из товара")
    @DeleteMapping("/{id}/category/{categoryId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> removeCategoryToProduct(@PathVariable Long id, @PathVariable Long categoryId){
        productService.removeCategoryFromProduct(id, categoryId);
        return ResponseEntity.noContent().build();
    }
}
