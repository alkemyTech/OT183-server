package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OrganizationPublicDataDto {

    private String name;
    private String image;
    private String address;
    private String phone;
    private List<SlideDetailedDto2> slides;

}
