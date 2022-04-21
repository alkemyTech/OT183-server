package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    @NotBlank(message = "{name.null}")
    private String name;

    @NotBlank(message = "{phone.null}")
    private String phone;

    @Email(message = "{email.format}")
    @NotBlank(message = "{email.null}")
    private String email;

    @Size(min = 50, message = "{message.size}")
    @NotBlank(message = "{message.null}")
    private String message;

}
