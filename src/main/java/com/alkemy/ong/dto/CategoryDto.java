package com.alkemy.ong.dto;

import lombok.*;

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
