package com.alkemy.ong.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class NewsDto {

    private Long id;

    @NotBlank(message = "Name must not be empty.")
    private String name;

    @NotBlank(message = "Content must not be empty.")
    private String content;

    @NotBlank(message = "Image must not be empty.")
    private String image;

    private String category;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate updatedDate;
}

