package com.alkemy.ong.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Setter
@Getter
public class CategoryDto {

    private Long id;

    @NotBlank(message = "Name must not be empty.")
    private String name;

    private String description;

    private String image;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate created;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate updated;
}
