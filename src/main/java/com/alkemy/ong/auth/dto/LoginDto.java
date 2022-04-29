package com.alkemy.ong.auth.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Setter
@Getter
public class LoginDto {

    @Email
    private String email;
    private String password;
}
