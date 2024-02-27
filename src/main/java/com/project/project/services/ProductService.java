package com.project.project.services;

import com.project.project.dtos.ProductAddDto;
import com.project.project.dtos.ProductsResponseDto;
import com.project.project.exceptions.AppError;
import com.project.project.models.Product;
import com.project.project.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> addProduct(@RequestBody ProductAddDto productAddDto) {
        if(productRepository.findByItemName(productAddDto.getItemName())!=null){
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Продукт с таким именем уже существует"), HttpStatus.BAD_REQUEST);
        }

        Product product = new Product();
        product.setItemName(productAddDto.getItemName());
        product.setItemPrice(productAddDto.getItemPrice());
        product.setItemQuantity(productAddDto.getItemQuantity());
        product.setCreatedAt(LocalDateTime.now());
        productRepository.save(product);
        return ResponseEntity.ok(new ProductsResponseDto(product.getId(), product.getItemName(), product.getItemPrice(), product.getItemQuantity()));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll(Sort.by("itemName"));
    }

    public boolean removeProduct(Long id) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Продукт с таким Id не найден"));
        if (existingProduct == null) {
            return false;
        }

        productRepository.delete(existingProduct);
        return true;

    }

    public ResponseEntity<?> getProductById(Long id){
        return productRepository.findById(id).map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public boolean buyProduct(Long id, int itemQuantity){

        return productRepository.findById(id).map(product -> {
            if (product.getItemQuantity()>=itemQuantity){
                product.setItemQuantity(product.getItemQuantity()-itemQuantity);
                productRepository.save(product);
                return true;
            }
            return false;
        }).orElse(false);
    }


    public void editProduct(Product product){
        product.setCreatedAt(LocalDateTime.now());
        productRepository.save(product);
    }

}


