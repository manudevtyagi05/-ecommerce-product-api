package com.ecommerce.ecommerce_api.service;

import com.ecommerce.ecommerce_api.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto , Long productId);

    void deleteProduct(Long productId);

    ProductDto getProductById(Long productId);

    List<ProductDto> getAllProducts();

    List<ProductDto> getProductsByCategory(Long categoryId);
}
