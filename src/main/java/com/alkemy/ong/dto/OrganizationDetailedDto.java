package com.alkemy.ong.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter

public class OrganizationDetailedDto {

    private Long id;
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
    private LocalDate created;
    private LocalDate updated;
    private String facebook;
    private String instagram;
    private String linkedin;

}
