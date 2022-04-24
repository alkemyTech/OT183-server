package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.exception.ParamNotFound;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Optional;

public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private MessageSource message;

    public CategoryDto updateCategory(CategoryDto categoryDto, Integer id) {

        Optional<Category> categoryModel = categoryRepository.findById(id);
        if(!categoryModel.isPresent()){
            throw new ParamNotFound(message.getMessage("error.id",null, Locale.US));
        }

        categoryMapper.updateModel(categoryModel.get(),categoryDto);
        categoryRepository.save(categoryModel.get());
        CategoryDto categoryUpdateDto = categoryMapper.CategoryModel2CategoryDto(categoryModel.get());

        return categoryUpdateDto;
    }


}
