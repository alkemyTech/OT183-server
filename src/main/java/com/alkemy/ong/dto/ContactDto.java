package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDto {

    private Long id;

    @NotBlank(message = "{error.empty_field}")
    private String name;

    private String phone;

    @NotBlank(message = "{error.empty_field}")
    @Email(message = "{error.invalid_email}")
    private String email;

    private String message;

    @Builder
    public ContactDto(String name, String phone, String mail, String message){
        this.name=name;
        this.phone=phone;
        this.email=mail;
        this.message=message;
    }


}
