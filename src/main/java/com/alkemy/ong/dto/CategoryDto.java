package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Long id;

    @NotNull(message = "Name can't be null")
    private String name;

    private String description;

    private String image;

    private String created;

    private String updated;


}
