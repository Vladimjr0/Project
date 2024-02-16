package com.project.project.repositories;

import com.project.project.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByItemName(String itemName);

}
