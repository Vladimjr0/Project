package com.project.project.repositories;

import com.project.project.models.Category;
import com.project.project.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

      Product findByItemName(String itemName);

      boolean existsByItemName(String itemName);

      List<Product> findByCategoriesContaining(Category category);

}
