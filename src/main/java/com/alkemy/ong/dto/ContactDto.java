package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    @NotNull(message = "{name.null}")
    @NotEmpty(message = "{name.empty}")
    @NotBlank(message = "{field.blank}")
    private String name;

    @NotNull(message = "{phone.null}")
    @NotEmpty(message = "{phone.empty}")
    @NotBlank(message = "{field.blank}")
    private String phone;

    @NotNull(message = "{email.blank}")
    @Email(message = "{email.format}")
    @NotBlank(message = "{field.blank}")
    private String email;

    @NotNull(message = "{message.null}")
    @Size(min = 50, message = "{message.size}")
    @NotBlank(message = "{field.blank}")
    private String message;

}
