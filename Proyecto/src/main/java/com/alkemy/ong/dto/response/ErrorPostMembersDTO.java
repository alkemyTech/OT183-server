package com.alkemy.ong.dto.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorPostMembersDTO {
    private Boolean error;
    private String message;
    private int number;
}
