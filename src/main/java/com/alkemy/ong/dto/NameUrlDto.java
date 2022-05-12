package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class NameUrlDto {

    PaginationUrlDto pages;
    List<Object> list;

}
