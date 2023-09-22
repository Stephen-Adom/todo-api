package com.alaska.todoapi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alaska.todoapi.Exception.UserDoesNotExistException;
import com.alaska.todoapi.Exception.ValidationErrorException;
import com.alaska.todoapi.customUtils.DataTransferObject;
import com.alaska.todoapi.customUtils.ResponseBody;
import com.alaska.todoapi.entity.Todo;
import com.alaska.todoapi.service.TodoService;

import jakarta.validation.Valid;

@RestController
@ResponseStatus
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private ResponseBody responseBody;

    @PostMapping("user/{id}/todo")
    public ResponseEntity<Map<String, Object>> createTodo(@PathVariable("id") Long id, @Valid @RequestBody Todo todo,
            BindingResult validationResult) throws ValidationErrorException, UserDoesNotExistException {
        if (validationResult.hasErrors()) {
            throw new ValidationErrorException(validationResult.getFieldErrors());
        }

        Todo newTodo = this.todoService.createTodo(id, todo);

        return new ResponseEntity<Map<String, Object>>(
                this.responseBody.responseBody(HttpStatus.CREATED, this.todoDataObject(newTodo)),
                HttpStatus.CREATED);
    }

    private DataTransferObject todoDataObject(Todo newTodo) {
        new DataTransferObject();
        DataTransferObject parsedObject = DataTransferObject.builder()
                .title(newTodo.getTitle())
                .description(newTodo.getDescription())
                .dueDate(newTodo.getDueDate())
                .createdAt(newTodo.getCreatedAt())
                .updatedAt(newTodo.getUpdatedAt())
                .completed(newTodo.getCompleted())
                .id(newTodo.getId())
                .user_id(newTodo.getUser().getId()).build();

        return parsedObject;
    }
}
