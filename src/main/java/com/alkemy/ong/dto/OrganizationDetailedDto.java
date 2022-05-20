package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDetailedDto {
    @Pattern(regexp = "^[A-z(\\s.,)]+",message = "{error.contains_numbers}")
    private String name;
    private String image;
    private String address;
    @Pattern(regexp = "^[0-9]+", message = "{error.phone}")
    private String phone;
    @Email
    private String email;
    private String welcomeText;
    private String aboutUsText;
    private String facebook;
    private String instagram;
    private String linkedin;

}
