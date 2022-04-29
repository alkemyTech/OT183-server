package com.alkemy.ong.auth.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;


@Getter
@Setter

public class UserProfileDto {

    private String firstName;
    private String lastName;
    private String photo;
    @Email
    private String email;
}
