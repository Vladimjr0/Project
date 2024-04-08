package com.project.project.controllers;


import com.project.project.dtos.ProductAddDto;
import com.project.project.dtos.ProductsResponseDto;
import com.project.project.models.Product;
import com.project.project.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@Tag(name = "Контроллер для взаимодействия с товарами")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Метод с помощью которого мы создаем новый товар")
    @PostMapping("/createProduct")
    public ResponseEntity<?> addNewProduct(@RequestBody ProductAddDto productAddDto) {
        return ResponseEntity.ok(productService.createProduct(productAddDto));
    }

    @Operation(summary = "Метод, позволяющий посмотреть список всех товаров")
    @GetMapping("/products")
    public List<ProductsResponseDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Метод, удаляющий товар по id")
    @GetMapping("/removeproduct/{id}")
    public ResponseEntity<?> removeProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Метод, позволяющий посмотреть информацию о каком-то конкретном товаре по id")
    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

//    @PostMapping("/buy/{id}")
//    public ResponseEntity<?> buyProduct(@RequestBody Map<String, Integer> requestBody, @PathVariable Long id) {
//        return productService.buyProduct(id,  requestBody.get("itemQuantity"));
//    }

    @Operation(summary = "Метод для обновлении информации о товаре")
    @PostMapping("/update/{id}")
    public ResponseEntity<?> editProduct(@PathVariable Long id, @RequestBody ProductAddDto productAddDto) {
        return ResponseEntity.ok(productService.updateProduct(productAddDto, id));
    }
}
