package com.alaska.todoapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alaska.todoapi.Exception.UserDoesNotExistException;
import com.alaska.todoapi.Exception.UserExistValidationException;
import com.alaska.todoapi.Exception.ValidationErrorException;
import com.alaska.todoapi.customUtils.ResponseBody;
import com.alaska.todoapi.entity.Todo;
import com.alaska.todoapi.entity.User;
import com.alaska.todoapi.entity.validationInterface.EditUserValidationInterface;
import com.alaska.todoapi.service.UserService;

import jakarta.validation.Valid;

@RestController
@ResponseStatus
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ResponseBody responseBody;

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        List<User> allUsers = this.userService.getAllUsers();
        return new ResponseEntity<Map<String, Object>>(this.responseBody.listResponseBody(HttpStatus.OK, allUsers),
                HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<Map<String, Object>> saveUser(
            @Valid @RequestBody User user, BindingResult validationResult)
            throws ValidationErrorException, UserExistValidationException {

        if (validationResult.hasErrors()) {
            throw new ValidationErrorException(validationResult.getFieldErrors());
        }

        User newUser = this.userService.saveUser(user);

        if (newUser.getTodos() == null) {
            newUser.setTodos(new ArrayList<Todo>());
        }
        return new ResponseEntity<Map<String, Object>>(this.responseBody.responseBody(HttpStatus.CREATED, newUser),
                HttpStatus.CREATED);
    }

    @PatchMapping("/user/{id}/edit")
    public ResponseEntity<Map<String, Object>> updateUserInfo(@PathVariable("id") Long id,
            @Validated(EditUserValidationInterface.class) @RequestBody User user, BindingResult validationResult)
            throws UserDoesNotExistException, UserExistValidationException, ValidationErrorException {

        if (validationResult.hasErrors()) {
            throw new ValidationErrorException(validationResult.getFieldErrors());
        }

        User updatedUser = this.userService.updateUser(id, user);

        return new ResponseEntity<Map<String, Object>>(this.responseBody.responseBody(HttpStatus.CREATED, updatedUser),
                HttpStatus.CREATED);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable("id") Long id)
            throws UserDoesNotExistException {
        User user = this.userService.getUserById(id);

        return new ResponseEntity<Map<String, Object>>(this.responseBody.responseBody(HttpStatus.OK, user),
                HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}/delete")
    public ResponseEntity<Map<String, String>> deleteUserById(@PathVariable("id") Long id)
            throws UserDoesNotExistException {
        this.userService.deleteUserById(id);
        Map<String, String> responseMessage = new HashMap<String, String>();
        responseMessage.put("status", HttpStatus.OK.toString());
        responseMessage.put("message", "User with id " + id + " has been deleted");

        return new ResponseEntity<Map<String, String>>(responseMessage, HttpStatus.OK);
    }
}
