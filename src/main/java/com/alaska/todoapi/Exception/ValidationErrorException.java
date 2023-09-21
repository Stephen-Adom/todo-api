package com.alaska.todoapi.Exception;

import java.util.List;

import org.springframework.validation.FieldError;

public class ValidationErrorException extends Exception {

    private List<FieldError> fieldError;

    public ValidationErrorException() {
        super();
    }

    public ValidationErrorException(List<FieldError> error) {
        super();
        this.fieldError = error;
    }

    public List<FieldError> getFieldErrors() {
        return this.fieldError;
    }
}
