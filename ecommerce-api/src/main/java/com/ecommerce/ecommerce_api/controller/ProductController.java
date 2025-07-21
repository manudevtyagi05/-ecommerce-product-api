package com.ecommerce.ecommerce_api.controller;

import com.ecommerce.ecommerce_api.dto.ProductDto;
import com.ecommerce.ecommerce_api.dto.ProductResponse;
import com.ecommerce.ecommerce_api.payload.ApiResponse;
import com.ecommerce.ecommerce_api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // CREATE
    @PostMapping
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@RequestBody ProductDto productDto) {
        ProductDto created = productService.createProduct(productDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Product created successfully", created));
    }

    // UPDATE
    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(@RequestBody ProductDto productDto, @PathVariable Long productId) {
        ProductDto updated = productService.updateProduct(productDto, productId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Product updated successfully", updated));
    }

    // DELETE
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Product deleted successfully", null));
    }

    // GET BY ID
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductDto>> getProductById(@PathVariable Long productId) {
        ProductDto product = productService.getProductById(productId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Product fetched successfully", product));
    }

    // GET ALL PRODUCTS
    @GetMapping
    public ResponseEntity<ApiResponse<ProductResponse>> getAllProducts(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "productId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(new ApiResponse<>(true, "All products fetched successfully", productResponse),HttpStatus.OK);
    }

    // GET PRODUCTS BY CATEGORY
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getProductsByCategory(@PathVariable Long categoryId) {
        List<ProductDto> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Products by category fetched successfully", products));
    }

    // GET PRODUCTS BY PRICE-RANGE
    @GetMapping("/price-range")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getProductsByPriceRange(
            @RequestParam("min") Double minPrice,
            @RequestParam("max") Double maxPrice
    ) {
        List<ProductDto> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(new ApiResponse<>(true, "Products filtered by price", products));
    }


}
