package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApiErrorDto<T> {

    private HttpStatus status;
    private T errors;

}
