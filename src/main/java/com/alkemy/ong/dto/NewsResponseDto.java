package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewsResponseDto {
    private String name;
    private String content;
    private String image;
    private Long categoryId;
}
