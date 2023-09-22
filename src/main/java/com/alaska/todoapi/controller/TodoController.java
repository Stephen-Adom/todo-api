package com.alaska.todoapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alaska.todoapi.Exception.TodoAlreadyMarkedAsCompletedException;
import com.alaska.todoapi.Exception.TodoDoesNotExistException;
import com.alaska.todoapi.Exception.UserDoesNotExistException;
import com.alaska.todoapi.Exception.ValidationErrorException;
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

    @GetMapping("/user/{id}/todos")
    public ResponseEntity<Map<String, Object>> getAllUserTodos(@PathVariable("id") Long id)
            throws UserDoesNotExistException {
        List<Todo> allTodos = this.todoService.getAllUserTodo(id);

        return new ResponseEntity<Map<String, Object>>(this.responseBody.todoListResponseBody(HttpStatus.OK, allTodos),
                HttpStatus.OK);
    }

    @PostMapping("user/{id}/todo")
    public ResponseEntity<Map<String, Object>> createTodo(@PathVariable("id") Long id, @Valid @RequestBody Todo todo,
            BindingResult validationResult) throws ValidationErrorException, UserDoesNotExistException {
        if (validationResult.hasErrors()) {
            throw new ValidationErrorException(validationResult.getFieldErrors());
        }

        Todo newTodo = this.todoService.createTodo(id, todo);

        return new ResponseEntity<Map<String, Object>>(
                this.responseBody.todoResponseBody(HttpStatus.CREATED, newTodo),
                HttpStatus.CREATED);
    }

    @PutMapping("/user/{userId}/todo/{id}/edit")
    public ResponseEntity<Map<String, Object>> updateTodo(@PathVariable("userId") Long userId,
            @PathVariable("id") Long todoId, @Valid @RequestBody Todo todo, BindingResult validationResult)
            throws ValidationErrorException, UserDoesNotExistException, TodoDoesNotExistException,
            TodoAlreadyMarkedAsCompletedException {

        if (validationResult.hasErrors()) {
            throw new ValidationErrorException(validationResult.getFieldErrors());
        }

        Todo updatedTodo = this.todoService.updateTodo(userId, todoId, todo);

        return new ResponseEntity<Map<String, Object>>(this.responseBody.todoResponseBody(HttpStatus.OK, updatedTodo),
                HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/todo/{id}")
    public ResponseEntity<Map<String, Object>> getUserTodoById(@PathVariable("userId") Long userId,
            @PathVariable("id") Long todoId) throws UserDoesNotExistException, TodoDoesNotExistException {
        Todo todo = this.todoService.getUserTodoById(userId, todoId);

        return new ResponseEntity<Map<String, Object>>(this.responseBody.todoResponseBody(HttpStatus.OK, todo),
                HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}/todo/{id}/delete")
    public ResponseEntity<Map<String, String>> deleteTodoById(@PathVariable("userId") Long userId,
            @PathVariable("id") Long todoId) throws UserDoesNotExistException, TodoDoesNotExistException {
        this.todoService.deleteTodoById(userId, todoId);

        Map<String, String> responseMessage = new HashMap<String, String>();
        responseMessage.put("status", HttpStatus.OK.toString());
        responseMessage.put("message", "Todo with id " + todoId + " has been deleted");

        return new ResponseEntity<Map<String, String>>(responseMessage, HttpStatus.OK);
    }
}
