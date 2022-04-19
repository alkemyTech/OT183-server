package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @NotNull(message="First name can't be null")
    private String firstName;

    @NotNull(message="Last name can't be null")
    private String lastName;

    @NotNull(message="Password can't be null")
    private String password;

    @Email
    @NotNull(message="Email can't be null")
    private String email;

    private String photo;

    private LocalDate updated;

    private LocalDate created;

    private Long roleid;
}
