package com.alkemy.ong.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.NumberFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsUpdateDTO {

    @NotBlank(message = "The name cant be null")
    @ApiModelProperty(example = "News new name", position = 0)
    private String name;

    @NotBlank(message = "The content cant be null")
    @ApiModelProperty(example = "News new content", position = 1)
    private String content;

    @NotBlank(message = "The image cant be null")
    @ApiModelProperty(example = "https://new-imagen-news-1.s3.amazonaws.com/image.png", position = 2)
    private String image;

    @NumberFormat
    @ApiModelProperty(example = "16", position = 3)
    private Long category;
    
}