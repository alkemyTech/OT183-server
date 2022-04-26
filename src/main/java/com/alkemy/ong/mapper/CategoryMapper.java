package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto entityToDto(Category entity) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(entity.getId());
        categoryDto.setName(entity.getName());
        categoryDto.setImage(entity.getImage());
        categoryDto.setDescription(entity.getDescription());
        categoryDto.setCreated(entity.getCreated());
        categoryDto.setUpdated(entity.getUpdated());
        return categoryDto;
    }
}
