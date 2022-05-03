package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SlideDetailedDto {

    private Long id;
    private String imageUrl;
    private String text;
    private Integer position;
    private Object organization;

}
