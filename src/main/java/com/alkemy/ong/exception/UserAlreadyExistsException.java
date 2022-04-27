package com.alkemy.ong.exception;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String message){ super(message);}
}
