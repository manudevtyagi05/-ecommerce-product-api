package com.ecommerce.ecommerce_api.service.impl;

import com.ecommerce.ecommerce_api.dto.ProductDto;
import com.ecommerce.ecommerce_api.dto.ProductResponse;
import com.ecommerce.ecommerce_api.entity.Category;
import com.ecommerce.ecommerce_api.entity.Product;
import com.ecommerce.ecommerce_api.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce_api.repository.CategoryRepository;
import com.ecommerce.ecommerce_api.repository.ProductRepository;
import com.ecommerce.ecommerce_api.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ModelMapper modelMapper;


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
    public ProductResponse getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir){

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> pageProducts = productRepo.findAll(pageable);

        List<ProductDto> content = pageProducts
                .getContent()
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();

        return ProductResponse.builder()
                .content(content)
                .pageNumber(pageProducts.getNumber())
                .pageSize(pageProducts.getSize())
                .totalElements(pageProducts.getTotalElements())
                .totalPages(pageProducts.getTotalPages())
                .lastPage(pageProducts.isLast())
                .build();
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

    @Override
    public List<ProductDto> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        List<Product> products = productRepo.findByPriceBetween(minPrice,maxPrice);
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
