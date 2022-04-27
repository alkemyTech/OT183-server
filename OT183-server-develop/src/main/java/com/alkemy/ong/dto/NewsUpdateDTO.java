package com.alkemy.ong.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsUpdateDTO {

    @NotBlank(message = "The name cant be null")
    private String name;

    @NotBlank(message = "The content cant be null")
    private String content;

    @NotBlank(message = "The image cant be null")
    private String image;

    @NumberFormat
    private Long category;
    
}
