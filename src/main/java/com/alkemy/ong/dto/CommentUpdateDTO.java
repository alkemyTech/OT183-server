package com.alkemy.ong.dto;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentUpdateDTO {
    @NotBlank(message = "{error.empty_fiel}")
    private String body;
}
