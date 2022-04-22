package com.alkemy.ong.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class TestimonialDetailedDto {

    private Long id;

    private String name;

    private String image;

    private String content;

    private LocalDate created;

    private LocalDate updated;

}
