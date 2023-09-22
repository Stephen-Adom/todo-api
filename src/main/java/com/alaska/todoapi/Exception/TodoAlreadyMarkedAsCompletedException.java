package com.alaska.todoapi.Exception;

public class TodoAlreadyMarkedAsCompletedException extends Exception {
    public TodoAlreadyMarkedAsCompletedException(String message) {
        super(message);
    }
}
