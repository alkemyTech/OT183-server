package com.alkemy.ong.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApiErrorResponse<T> {

    private HttpStatus status;
    private T errors;

}