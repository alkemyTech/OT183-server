package com.alkemy.ong.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ActivityDto {

    private Long id;

    @NotNull(message = "Name must not be empty.")
    private String name;

    @NotNull(message = "Content must not be empty.")
    private String content;

    @NotNull(message = "Image must not be empty.")
    private String image;
}
