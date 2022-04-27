package com.alkemy.ong.service;

import com.alkemy.ong.dto.CategoryDto;

public interface ICategoryService {
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id);

    public CategoryDto addCategory(CategoryDto dto);
    public CategoryDto getById(Long id);
}
