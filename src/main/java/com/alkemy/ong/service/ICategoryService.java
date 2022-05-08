package com.alkemy.ong.service;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.CategoryNameDto;
import com.alkemy.ong.dto.CategoryNameUrlDto;

import java.awt.print.Pageable;
import java.util.List;

public interface ICategoryService {
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id);

    public CategoryDto addCategory(CategoryDto dto);

    CategoryNameUrlDto returnList(Integer page);

    public CategoryDto getById(Long id);

    void deleteCategory(Long id);

}
