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

//    @PostMapping("/buy/{id}")
//    public ResponseEntity<?> buyProduct(@RequestBody Map<String, Integer> requestBody, @PathVariable Long id) {
//        return productService.buyProduct(id,  requestBody.get("itemQuantity"));
//    }

    @Operation(summary = "Метод для обновлении информации о товаре")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ProductsResponseDto> editProduct(@PathVariable Long id, @RequestBody ProductAddDto productAddDto) {
        return ResponseEntity.ok(productService.updateProduct(productAddDto, id));
    }
}
