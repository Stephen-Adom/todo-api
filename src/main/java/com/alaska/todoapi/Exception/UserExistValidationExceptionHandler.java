package com.alaska.todoapi.Exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class UserExistValidationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserExistValidationException.class)
    public ResponseEntity<ErrorMessage> userExistValidation(UserExistValidationException exception) {
        List<String> messageList = new ArrayList<String>();
        messageList.add(exception.getMessage());

        return new ResponseEntity<ErrorMessage>(new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY, messageList),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
