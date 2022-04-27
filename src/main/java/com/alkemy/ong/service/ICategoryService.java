package com.alkemy.ong.service;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.CategoryNameDto;

import java.util.List;

public interface ICategoryService {

    public CategoryDto addCategory(CategoryDto dto);
    CategoryDto getById(Long id);

    List<CategoryNameDto> returnList();
}
