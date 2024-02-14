package com.project.project.services;

import com.project.project.models.Product;
import com.project.project.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public boolean addProduct(Product product) {
        Product existingProduct = productRepository.findByItemName(product.getItemName());
        if (existingProduct != null) {
            return false;
        }

        product.setCreatedAt(LocalDateTime.now());

        productRepository.save(product);
        return true;
    }


}


