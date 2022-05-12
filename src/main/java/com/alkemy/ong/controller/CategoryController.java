package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.NameUrlDto;
import com.alkemy.ong.service.ICategoryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @ApiOperation (value = "Get categories", notes = "Get categories by size pagination")
    @ApiImplicitParam (name = "page", value = "number page", required = true, dataType = "Integer")
    @GetMapping
    public ResponseEntity<NameUrlDto> getAll(@RequestParam(required = false) Integer page){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.returnList(page));
    }

    @ApiOperation (value = "Update category")
    @ApiImplicitParams({
            @ApiImplicitParam (name = "id", value = "Id Category", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam (name = "categoryDto", required = true, dataType = "CategoryDto")
    })
    @PutMapping(path = "/{id}")
    public ResponseEntity<CategoryDto> updateCategory (@RequestBody CategoryDto categoryDto, @PathVariable Long id) {

        CategoryDto categoryUpdateDto = categoryService.updateCategory(categoryDto, id);
        return ResponseEntity.ok().body(categoryUpdateDto);
    }

    @ApiOperation (value = "Get category", notes = "get category by id")
    @ApiImplicitParam (name = "id", value = "Id category", required = true, dataType = "Long", paramType = "path")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(name = "id") long id){
        return new ResponseEntity<>(categoryService.getById(id), HttpStatus.OK);
    }

    @ApiOperation (value = "Create category")
    @ApiImplicitParam (name = "dto", required = true, dataType = "CategoryDto")
    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto dto){

        CategoryDto category = categoryService.addCategory(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @ApiOperation (value = "Delete category", notes = "Delete category by id")
    @ApiImplicitParam (name = "id", value = "Id category", required = true, dataType = "Long", paramType = "path")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){

        categoryService.deleteCategory(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
