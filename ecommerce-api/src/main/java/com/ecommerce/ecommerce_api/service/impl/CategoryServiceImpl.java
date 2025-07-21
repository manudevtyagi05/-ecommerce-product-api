package com.ecommerce.ecommerce_api.service.impl;

import com.ecommerce.ecommerce_api.dto.CategoryDto;
import com.ecommerce.ecommerce_api.entity.Category;
import com.ecommerce.ecommerce_api.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce_api.repository.CategoryRepository;
import com.ecommerce.ecommerce_api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.dtoToCategory(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return this.categoryToDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","ID", categoryId));

        category.setName(categoryDto.getName());
        category.setDescription(category.getDescription());

        Category updatedCategory = categoryRepository.save(category);
        return this.categoryToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","ID", categoryId));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));
        return this.categoryToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::categoryToDto).collect(Collectors.toList());
    }

    //Mapper methods
    private Category dtoToCategory(CategoryDto dto){
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }

    private CategoryDto categoryToDto(Category category){
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }
}
