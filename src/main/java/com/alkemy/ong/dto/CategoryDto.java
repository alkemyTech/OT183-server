package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Long id;

    @NotBlank(message = "{error.empty_field}")
    @Pattern(regexp = "^[A-z(\\s.,)]+",message = "{error.contains_numbers}")
    private String name;

    private String description;

    private String image;

    private String created;

    private String updated;

    private boolean deleted;


}
