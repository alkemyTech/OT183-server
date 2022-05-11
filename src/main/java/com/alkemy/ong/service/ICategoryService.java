package com.alkemy.ong.service;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.NameUrlDto;

public interface ICategoryService {
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id);

    public CategoryDto addCategory(CategoryDto dto);

    NameUrlDto returnList(Integer page);

    public CategoryDto getById(Long id);

    void deleteCategory(Long id);

}
