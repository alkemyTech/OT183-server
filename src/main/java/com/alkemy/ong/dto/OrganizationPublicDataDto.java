package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class OrganizationPublicDataDto {

    private String name;
    private String image;
    private String address;
    private int phone;

}
