package com.alkemy.ong.controller;


import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.service.ICategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private ICategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto dto){

        CategoryDto category = categoryService.addCategory(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }
}
