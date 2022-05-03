package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlideRequestDto {

    private String image;
    private String text;
    private String position;
    private Long organizationId;
}
