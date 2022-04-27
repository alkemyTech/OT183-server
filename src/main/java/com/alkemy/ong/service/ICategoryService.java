package com.alkemy.ong.service;

import com.alkemy.ong.dto.CategoryDto;

public interface ICategoryService {

    public CategoryDto addCategory(CategoryDto dto);
    CategoryDto getById(Long id);
}
