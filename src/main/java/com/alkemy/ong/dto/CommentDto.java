package com.alkemy.ong.dto;


import lombok.Data;

import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentDto {

    private Long id;

    @NotNull(message = "{error.empty_fiel}")
    private Long newsId;

    @NotNull(message = "{error.empty_fiel}")
    private Long userId;

    @NotBlank(message = "{error.empty_fiel}")
    private String body;
}
