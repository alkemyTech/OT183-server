package com.alkemy.ong.exception;

public class ParamErrorException extends RuntimeException {
    public ParamErrorException(String error) {
        super(error);
    }
}
