package com.ecommerce.ecommerce_api.service.impl;

import com.ecommerce.ecommerce_api.dto.ProductDto;
import com.ecommerce.ecommerce_api.entity.Category;
import com.ecommerce.ecommerce_api.entity.Product;
import com.ecommerce.ecommerce_api.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce_api.repository.CategoryRepository;
import com.ecommerce.ecommerce_api.repository.ProductRepository;
import com.ecommerce.ecommerce_api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CategoryRepository categoryRepo;


    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Category category = categoryRepo.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productDto.getCategoryId()));

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setCategory(category);

        Product savedProduct = productRepo.save(product);
        return mapToDto(savedProduct);

    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new  ResourceNotFoundException("Product", "id", productId));

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());

        //Update category if provided
        if (productDto.getCategoryId() !=null){
            Category category = categoryRepo.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category" , "id" , productDto.getCategoryId()));
            product.setCategory(category);
        }


        return mapToDto(productRepo.save(product));

    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id" , productId));
        productRepo.delete(product);

    }

    @Override
    public ProductDto getProductById(Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id" ,productId));
        return mapToDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepo.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategory(Long categoryId) {
       Category category = categoryRepo.findById(categoryId)
               .orElseThrow(() -> new ResourceNotFoundException("category", "id", categoryId));

       List<Product> products = productRepo.findByCategory(category);
       return products.stream()
               .map(this::mapToDto)
               .collect(Collectors.toList());
    }

    // Mapping helper method
    private ProductDto mapToDto(Product product){
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setCategoryId(product.getCategory().getId());
        return dto;
    }
}
