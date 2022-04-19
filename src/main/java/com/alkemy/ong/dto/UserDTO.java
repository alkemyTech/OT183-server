package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data

public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String password;

    private String email;

    private String photo;

    private LocalDate updated;

    private LocalDate created;

    private Long roleid;
}
