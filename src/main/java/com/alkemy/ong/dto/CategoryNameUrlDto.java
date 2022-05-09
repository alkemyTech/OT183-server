package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CategoryNameUrlDto {

    PaginationUrlDto pages;
    List<CategoryNameDto> categories;

}
