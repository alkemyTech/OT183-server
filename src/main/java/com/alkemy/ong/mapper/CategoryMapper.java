package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto CategoryModel2CategoryDto(Category model) {

        CategoryDto dto = new CategoryDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setImage(model.getImage());
        dto.setDescription(model.getDescription());
        return dto;

    }

    public void updateModel(Category categoryModel, CategoryDto categoryDto) {

        categoryModel.setName(categoryDto.getName());
        categoryModel.setImage(categoryDto.getImage());
        categoryModel.setDescription(categoryDto.getDescription());
    }
}
