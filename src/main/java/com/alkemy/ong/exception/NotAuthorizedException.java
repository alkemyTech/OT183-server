package com.alkemy.ong.exception;

public class NotAuthorizedException extends RuntimeException{

    public NotAuthorizedException(String message){
        super(message);
    }
}
