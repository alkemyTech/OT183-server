package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPatchDto {
    @NotEmpty(message = "{error.empty_field}")
    private String firstName;
    @NotEmpty(message = "{error.empty_field}")
    private String lastName;
    @NotEmpty(message = "{error.empty_field}")
    private String photo;
}
