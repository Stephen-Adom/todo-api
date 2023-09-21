package com.alaska.todoapi.Exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ValidationErrorExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationErrorException.class)
    public ResponseEntity<ErrorMessage> handleValidationError(ValidationErrorException validationErrorException) {
        List<String> errorList = new ArrayList<String>();

        for (FieldError error : validationErrorException.getFieldErrors()) {
            errorList.add(error.getDefaultMessage());
        }

        return new ResponseEntity<ErrorMessage>(new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY, errorList),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
