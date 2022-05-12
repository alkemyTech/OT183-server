package com.alkemy.ong.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class NewsDto {

    @ApiModelProperty(example = "1", position = 0)
    private Long id;

    @NotBlank(message = "{error.empty_field}")
    @ApiModelProperty(example = "News 1", position = 1)
    private String name;

    @NotBlank(message = "{error.empty_field}")
    @ApiModelProperty(example = "This is the content of the news", position = 2)
    private String content;

    @NotBlank(message = "{error.empty_field}")
    @ApiModelProperty(example = "https://imagen-news-1.s3.amazonaws.com/image.png", position = 3)
    private String image;

    @NotNull(message = "{error.empty_field}")
    @ApiModelProperty(example = "6", position = 4)
    private Long categoryId;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @ApiModelProperty(example = "10-05-2022", position = 5)
    private LocalDate createdDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @ApiModelProperty(example = "10-05-2022", position = 6)
    private LocalDate updatedDate;
}

