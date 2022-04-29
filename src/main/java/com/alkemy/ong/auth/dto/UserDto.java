package com.alkemy.ong.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotNull(message="First name can't be null")
    private String firstName;

    @NotNull(message="Last name can't be null")
    private String lastName;

    @NotNull(message="Email can't be null")
    @Email(message = "Invalid email")
    private String email;

    @NotNull(message="Password can't be null")
    private String password;

    @NotNull
    private String photo;

    //TODO - In the future this roleId will point at to ROLE Class
    private Long roleid;

    private LocalDate updated;

    private LocalDate created;

}
