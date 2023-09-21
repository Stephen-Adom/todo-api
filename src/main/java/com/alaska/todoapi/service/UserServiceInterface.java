package com.alaska.todoapi.service;

import java.util.List;

import com.alaska.todoapi.Exception.UserExistValidationException;
import com.alaska.todoapi.entity.User;

public interface UserServiceInterface {

    public List<User> getAllUsers();

    public User saveUser(User user) throws UserExistValidationException;
}
