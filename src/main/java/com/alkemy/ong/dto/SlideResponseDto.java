package com.alkemy.ong.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SlideResponseDto {
    private Long id;
    private String imageUrl;
    private String text;
    private Integer position;
    private Long organizationId;
    private String organizationName;
}
