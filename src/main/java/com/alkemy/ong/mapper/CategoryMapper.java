package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.model.Category;
import org.springframework.stereotype.Component;
import com.alkemy.ong.util.MapperUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper implements IMapper<Category,CategoryDto>{

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


    public Category toEntity(CategoryDto dto) {

        return Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .deleted(dto.isDeleted())
                .image(dto.getImage()).build();
    }


    public Category toEntity(Long id, CategoryDto dto) {
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .image(dto.getImage()).build();
    }


    public CategoryDto toDto(Category entity) {
        return CategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .image(entity.getImage())
                .created(entity.getCreated().toString())
                .updated(entity.getUpdated().toString()).build();
    }


    public List<CategoryDto> toDtoList(List<Category> list) {
        return MapperUtil.streamListNonNull(list, this::toDto);
    }

}
