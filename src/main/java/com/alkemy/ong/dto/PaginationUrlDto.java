package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaginationUrlDto {

    private String previousUrl;
    private String nextUrl;

}
