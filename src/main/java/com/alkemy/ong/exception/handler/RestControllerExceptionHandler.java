package com.alkemy.ong.exception.handler;

import com.alkemy.ong.exception.ApiErrorResponse;
import com.alkemy.ong.exception.DataRepresentationException;
import com.alkemy.ong.exception.EmailException;
import com.alkemy.ong.exception.EmailSenderException;
import com.alkemy.ong.exception.NullListException;
import com.alkemy.ong.exception.NullModelException;
import com.alkemy.ong.exception.UserAlreadyExistsException;
import com.alkemy.ong.exception.UserNotFoundException;
import com.alkemy.ong.exception.UserRegistrationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        List<String> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        return handleExceptionInternal(
                ex,
                new ApiErrorResponse<>(
                        HttpStatus.BAD_REQUEST,
                        errors
                ),
                headers,
                status,
                request
        );

    }

    @ExceptionHandler(DataRepresentationException.class)
    public ResponseEntity<ApiErrorResponse<String>> handleDataRepresentationException(DataRepresentationException ex) {
        return new ResponseEntity<>(
                new ApiErrorResponse<>(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ex.getMessage()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(NullListException.class)
    public ResponseEntity<ApiErrorResponse<String>> handleDataRepresentationException(NullListException ex) {
        return new ResponseEntity<>(
                new ApiErrorResponse<>(
                        HttpStatus.BAD_REQUEST,
                        ex.getMessage()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<ApiErrorResponse<String>> handleEmailException(EmailException ex) {
        return new ResponseEntity<>(
                new ApiErrorResponse<>(
                        HttpStatus.BAD_REQUEST,
                        ex.getMessage()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<ApiErrorResponse<String>> handleUserRegistrationException(UserRegistrationException ex) {
        return new ResponseEntity<>(
                new ApiErrorResponse<>(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ex.getMessage()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(EmailSenderException.class)
    public ResponseEntity<ApiErrorResponse<String>> handleEmailSenderException(EmailSenderException ex) {
        return new ResponseEntity<>(
                new ApiErrorResponse<>(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ex.getMessage()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(NullModelException.class)
    public ResponseEntity<ApiErrorResponse<String>> handleDataRepresentationException(NullModelException ex) {
        return new ResponseEntity<>(
                new ApiErrorResponse<>(
                        HttpStatus.NOT_FOUND,
                        ex.getMessage()
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse<String>> handleUserAlredyExistsExcepetion(UserRegistrationException ex) {
        return new ResponseEntity<>(
                new ApiErrorResponse<>(
                        HttpStatus.BAD_REQUEST,
                        ex.getMessage()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse<String>> handleUserNotFoundException(UserNotFoundException ex){
        return new ResponseEntity<>(
                new ApiErrorResponse<>(
                        HttpStatus.NOT_FOUND,
                        ex.getMessage()
                ),
                HttpStatus.NOT_FOUND
        );
    }

}
