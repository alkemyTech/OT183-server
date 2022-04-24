package com.alkemy.ong.service;

import com.alkemy.ong.dto.CategoryDto;

public interface ICategoryService {
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer id);
}
