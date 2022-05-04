package com.alkemy.ong.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class NewsDto {

    private Long id;

    @NotBlank(message = "{error.empty_field}")
    private String name;

    @NotBlank(message = "{error.empty_field}")
    private String content;

    @NotBlank(message = "{error.empty_field}")
    private String image;

    private Long categoryId;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate updatedDate;
}

