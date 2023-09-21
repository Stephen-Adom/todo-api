package com.alaska.todoapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alaska.todoapi.Exception.UserExistValidationException;
import com.alaska.todoapi.Exception.ValidationErrorException;
import com.alaska.todoapi.entity.Todo;
import com.alaska.todoapi.entity.User;
import com.alaska.todoapi.service.UserService;

import jakarta.validation.Valid;

@RestController
@ResponseStatus
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        List<User> allUsers = this.userService.getAllUsers();
        return new ResponseEntity<Map<String, Object>>(this.listResponseBody(HttpStatus.OK, allUsers), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<Map<String, Object>> saveUser(@Valid @RequestBody User user, BindingResult validationResult)
            throws ValidationErrorException, UserExistValidationException {

        if (validationResult.hasErrors()) {
            throw new ValidationErrorException(validationResult.getFieldErrors());
        }

        User newUser = this.userService.saveUser(user);

        if (newUser.getTodos() == null) {
            newUser.setTodos(new ArrayList<Todo>());
        }
        return new ResponseEntity<Map<String, Object>>(this.responseBody(HttpStatus.CREATED, newUser),
                HttpStatus.CREATED);
    }

    private Map<String, Object> listResponseBody(HttpStatus status, List<User> users) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("status", status);
        body.put("data", users);

        return body;
    }

    private Map<String, Object> responseBody(HttpStatus status, User user) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("status", status);
        body.put("data", user);

        return body;
    }
}
