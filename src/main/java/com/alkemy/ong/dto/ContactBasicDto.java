package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactBasicDto {

    private String name;
    private String email;
    private String phone;
    private String message;
    private String created;


}
