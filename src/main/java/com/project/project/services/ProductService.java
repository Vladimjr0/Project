package com.project.project.services;

import com.project.project.models.Product;
import com.project.project.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public List<Product> getAllProducts() {
        return productRepository.findAll(Sort.by("itemName"));
    }

    public boolean removeProduct(String itemName) {
        Product existingProduct = productRepository.findByItemName(itemName);
        if (existingProduct == null) {
            return false;
        }

        productRepository.delete(existingProduct);
        return true;

    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Продукт с таким Id не найден"));
    }

    public boolean buyProduct(Long id, int itemQuantity){
        Product existingProduct = getProductById(id);
        if(existingProduct==null||existingProduct.getItemQuantity()-itemQuantity<0){
            return false;
        }
        existingProduct.setItemQuantity(existingProduct.getItemQuantity()-itemQuantity);
        productRepository.save(existingProduct);
        return true;

    }

}


