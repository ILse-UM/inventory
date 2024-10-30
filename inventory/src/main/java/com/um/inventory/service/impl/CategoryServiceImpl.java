package com.um.inventory.service.impl;

import com.um.inventory.dto.CategoryResponseDto;
import com.um.inventory.model.Category;
import com.um.inventory.repository.CategoryRepository;
import com.um.inventory.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(this::toCategoryDto).toList();
    }

    @Override
    public Optional<CategoryResponseDto> getCategoryById(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<CategoryResponseDto> getCategoryByName(String name) {
        return categoryRepository.findByName(name).map(this::toCategoryDto);
    }

    @Override
    public CategoryResponseDto createCategory(String name) {
        Category category = new Category();
        category.setName(name);

        categoryRepository.save(category);

        return toCategoryDto(category);
    }

    @Override
    public CategoryResponseDto updateCategory(int id, String name) {
        Category category = categoryRepository.findById(id).orElse(null);
        category.setName(name);
        categoryRepository.save(category);
        return toCategoryDto(category);
    }

    @Override
    public void deleteCategory(int id) {
        Category category = categoryRepository.findById(id).orElse(null);
        categoryRepository.delete(category);
    }

    private CategoryResponseDto toCategoryDto(Category category) {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setName(category.getName());
        return categoryResponseDto;
    }
}
