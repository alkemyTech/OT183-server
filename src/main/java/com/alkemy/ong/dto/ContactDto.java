package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    @NotNull(message = "{name.null}")
    private String name;

    @NotNull(message = "{phone.null}")
    private String phone;

    @NotNull(message = "{email.blank}")
    @Email(message = "{email.format}")
    private String email;

    @NotNull(message = "{message.null}")
    private String message;

}
