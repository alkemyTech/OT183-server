package com.alkemy.ong.auth.dto;

import com.alkemy.ong.model.Role;
import lombok.*;
import org.aspectj.lang.annotation.Before;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
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


    private Role role;

    private LocalDate updated;

    private LocalDate created;

    @Builder
    public UserDto(String firstName, String lastName, String email, String password, String photo, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.role = role;
    }
}
