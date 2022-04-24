package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")

public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @PutMapping(path = "/{id}")
    public ResponseEntity<CategoryDto> updateCategory (@RequestBody CategoryDto categoryDto, @PathVariable Integer id){

        CategoryDto categoryUpdateDto = categoryService.updateCategory(categoryDto,id);
        return ResponseEntity.ok().body(categoryUpdateDto);
    }
}
