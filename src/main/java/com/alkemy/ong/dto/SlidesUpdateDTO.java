package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlidesUpdateDTO {
    private String imageUrl;

    private String text;

    private String position;

    private Long organization;
}
