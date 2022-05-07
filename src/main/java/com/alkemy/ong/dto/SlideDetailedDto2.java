package com.alkemy.ong.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SlideDetailedDto2 {

    private Long id;
    private String imageUrl;
    private String text;
    private Integer position;

}
