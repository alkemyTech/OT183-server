package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter

public class UserProfileDto {

    @Column(name = "first_name",nullable = false )
    private String firstName;

    @Column(name = "last_name",nullable = false )
    private String lastName;

    @Column(name = "photo",nullable = false )
    private String photo;

    @Column(name = "email",nullable = false )
    @Email
    private String email;
}
