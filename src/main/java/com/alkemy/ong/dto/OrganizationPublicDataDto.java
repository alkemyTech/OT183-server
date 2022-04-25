package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrganizationPublicDataDto {

    private String name;
    private String image;
    private String address;
    private String phone;

}
