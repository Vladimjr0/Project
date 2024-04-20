package com.project.project.repositories;

import com.project.project.entities.Category;
import com.project.project.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

      Product findByItemName(String itemName);

      boolean existsByItemName(String itemName);

      List<Product> findByCategoriesContaining(Category category);

}
