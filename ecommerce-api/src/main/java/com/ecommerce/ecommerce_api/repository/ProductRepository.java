package com.ecommerce.ecommerce_api.repository;

import com.ecommerce.ecommerce_api.entity.Category;
import com.ecommerce.ecommerce_api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
}
