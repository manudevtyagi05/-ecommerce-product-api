package com.ecommerce.ecommerce_api.service;

import com.ecommerce.ecommerce_api.dto.ProductDto;
import com.ecommerce.ecommerce_api.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto , Long productId);

    void deleteProduct(Long productId);

    ProductDto getProductById(Long productId);

    ProductResponse getAllProducts(int pageNumber, int pageSize , String sortBy , String sortDir);

    List<ProductDto> getProductsByPriceRange(Double minPrice, Double maxPrice);

    List<ProductDto> getProductsByCategory(Long categoryId);
}
