package com.project.project.services;

import com.project.project.dtos.ProductAddDto;
import com.project.project.exceptions.AppError;
import com.project.project.models.Product;
import com.project.project.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> addProduct(@RequestBody ProductAddDto productAddDto) {
        if (productRepository.existsByItemName(productAddDto.getItemName())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Продукт с таким именем уже существует"), HttpStatus.BAD_REQUEST);
        }
        Product product = new Product();
        return productFieldSet(product, productAddDto);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll(Sort.by("itemName"));
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()
        -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));
    }

    public ResponseEntity<?> removeProductId(Long id) {
        if (!productRepository.existsById(id)) {
            return new ResponseEntity<>("Продукт с таким id не найден", HttpStatus.NOT_FOUND);
        }
        productRepository.deleteById(id);
        return ResponseEntity.ok("Продукт успешно удален.");
    }

    public ResponseEntity<?> buyProduct(Long id, int itemQuantity) {
        return productRepository.findById(id)
                .map(product -> {
                    if (product.getItemQuantity() >= itemQuantity) {
                        product.setItemQuantity(product.getItemQuantity() - itemQuantity);
                        productRepository.save(product);
                        return ResponseEntity.ok("Покупка успешно совершена");
                    }
                    return ResponseEntity.badRequest().body("Нет такого количества товара на складе ");
                }).orElse(new ResponseEntity<>("Продукт с таким id не найден", HttpStatus.NOT_FOUND));
    }


    public ResponseEntity<?> editProduct(ProductAddDto productAddDto, Long id) {
        return productRepository.findById(id)
                .map(product -> productFieldSet(product, productAddDto))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private ResponseEntity<?> productFieldSet(Product product, ProductAddDto productAddDto) {
        product.setItemName(productAddDto.getItemName());
        product.setItemPrice(productAddDto.getItemPrice());
        product.setItemQuantity(productAddDto.getItemQuantity());
        product.setCreatedAt(LocalDateTime.now());
        productRepository.save(product);
        return ResponseEntity.ok("Успешно");
    }

}


