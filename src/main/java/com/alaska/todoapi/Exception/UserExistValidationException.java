package com.alaska.todoapi.Exception;

public class UserExistValidationException extends Exception {

    public UserExistValidationException() {
        super();
    }

    public UserExistValidationException(String message) {
        super(message);
    }
}
