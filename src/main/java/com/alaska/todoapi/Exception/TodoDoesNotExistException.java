package com.alaska.todoapi.Exception;

public class TodoDoesNotExistException extends Exception {
    public TodoDoesNotExistException(String message) {
        super(message);
    }
}
