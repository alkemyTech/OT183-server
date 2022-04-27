package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.CategoryNameDto;
import com.alkemy.ong.exception.EntityNotFoundException;
import com.alkemy.ong.exception.NullListException;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;


@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private MessageSource messageSource;


    @Transactional
    public CategoryDto addCategory(CategoryDto dto){

        Category entity = categoryMapper.toEntity(dto);
        categoryRepository.save(entity);
        return categoryMapper.toDto(entity);

    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category", "id", id));
        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryNameDto> returnList(){
        List<Category> entityList = categoryRepository.findAll();
        if (entityList.size() == 0) {
            throw new NullListException(messageSource.getMessage("error.null_list", null, Locale.US));
        }
        return categoryMapper.listNameDto(entityList);
    }
}
