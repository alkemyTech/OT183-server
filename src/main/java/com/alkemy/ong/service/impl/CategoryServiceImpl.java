package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.NameUrlDto;
import com.alkemy.ong.exception.PaginationSizeOutOfBoundsException;
import com.alkemy.ong.exception.ParamNotFound;
import com.alkemy.ong.exception.EntityNotFoundException;
import com.alkemy.ong.exception.NullListException;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.ICategoryService;
import com.alkemy.ong.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private MessageSource message;

    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {

        Optional<Category> categoryModel = categoryRepository.findById(id);
        if(!categoryModel.isPresent()){
            throw new ParamNotFound(message.getMessage("error.id",null, Locale.US));
        }

        categoryMapper.updateModel(categoryModel.get(),categoryDto);
        categoryRepository.save(categoryModel.get());
        CategoryDto categoryUpdateDto = categoryMapper.CategoryModel2CategoryDto(categoryModel.get());

        return categoryUpdateDto;
    }

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
    public NameUrlDto returnList(Integer page){
        int pageNumber = PaginationUtil.resolvePageNumber(page);
        int maximumPageNumber = categoryRepository.getCategoriesQuantity() / 10;
        if (pageNumber > maximumPageNumber) {
            throw new PaginationSizeOutOfBoundsException(
                    message.getMessage("error.pagination_size", null, Locale.US)
            );
        }
        Pageable pageable = PageRequest.of(pageNumber, 10);
        List<Category> entityList = categoryRepository.findAll(pageable).toList();
        if (entityList.size() == 0) {
            throw new NullListException(message.getMessage("error.null_list", null, Locale.US));
        }

        return categoryMapper.listNameDto(entityList, PaginationUtil.getPreviousAndNextPage(pageNumber, maximumPageNumber));
    }

    public void deleteCategory(Long id) {

        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category", "id", id);
        }

        categoryRepository.deleteById(id);
    }

}
