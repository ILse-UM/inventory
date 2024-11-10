package com.um.inventory.service;

import com.um.inventory.dto.CategoryRequestDto;
import com.um.inventory.dto.CategoryResponseDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryResponseDto> getAllCategories();
    Optional<CategoryResponseDto> getCategoryById(int id);
    Optional<CategoryResponseDto> getCategoryByName(String name);
    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);
    CategoryResponseDto updateCategory(int id, CategoryRequestDto categoryRequestDto);
    void deleteCategory(int id);
}
