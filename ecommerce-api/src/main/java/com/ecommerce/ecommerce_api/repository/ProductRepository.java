package com.ecommerce.ecommerce_api.repository;

import com.ecommerce.ecommerce_api.entity.Category;
import com.ecommerce.ecommerce_api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);

    // Find products where price is between min and max
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);


}
