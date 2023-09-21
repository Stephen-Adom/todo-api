package com.alaska.todoapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alaska.todoapi.entity.User;
import com.alaska.todoapi.service.UserService;

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

    private Map<String, Object> listResponseBody(HttpStatus status, List<User> users) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("status", status);
        body.put("data", users);

        return body;
    }
}
