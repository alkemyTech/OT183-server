package com.alkemy.ong.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ActivityDto {

    private Long id;

    @NotBlank(message = "Name must not be empty.")
    private String name;

    @NotBlank(message = "Content must not be empty.")
    private String content;

    @NotBlank(message = "Image must not be empty.")
    private String image;
}
