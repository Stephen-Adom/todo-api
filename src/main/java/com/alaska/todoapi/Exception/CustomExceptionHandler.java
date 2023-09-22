package com.alaska.todoapi.Exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ValidationErrorException.class)
    public ResponseEntity<ErrorMessage> handleValidationError(ValidationErrorException validationErrorException) {
        List<String> errorList = new ArrayList<String>();

        for (FieldError error : validationErrorException.getFieldErrors()) {
            errorList.add(error.getDefaultMessage());
        }

        return new ResponseEntity<ErrorMessage>(new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY, errorList),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UserExistValidationException.class)
    public ResponseEntity<ErrorMessage> userExistValidation(UserExistValidationException exception) {
        List<String> messageList = new ArrayList<String>();
        messageList.add(exception.getMessage());

        return new ResponseEntity<ErrorMessage>(new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY, messageList),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<ErrorMessage> userNotExist(UserDoesNotExistException exception) {
        List<String> errorMessages = new ArrayList<String>();
        errorMessages.add(exception.getMessage());

        return new ResponseEntity<ErrorMessage>(new ErrorMessage(HttpStatus.NOT_FOUND, errorMessages),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TodoAlreadyMarkedAsCompletedException.class)
    public ResponseEntity<ErrorMessage> todoAlreadyCompleted(TodoAlreadyMarkedAsCompletedException exception) {
        List<String> errorMessages = new ArrayList<String>();
        errorMessages.add(exception.getMessage());

        return new ResponseEntity<ErrorMessage>(new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY, errorMessages),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(TodoDoesNotExistException.class)
    public ResponseEntity<ErrorMessage> todoNotExist(TodoDoesNotExistException exception) {
        List<String> errorMessages = new ArrayList<String>();
        errorMessages.add(exception.getMessage());

        return new ResponseEntity<ErrorMessage>(new ErrorMessage(HttpStatus.NOT_FOUND, errorMessages),
                HttpStatus.NOT_FOUND);
    }
}
