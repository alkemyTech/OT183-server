package com.alkemy.ong.service;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.CategoryNameDto;

import java.awt.print.Pageable;
import java.util.List;

public interface ICategoryService {
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id);

    public CategoryDto addCategory(CategoryDto dto);

    List<CategoryNameDto> returnList(Integer page);

    public CategoryDto getById(Long id);

    void deleteCategory(Long id);

}
