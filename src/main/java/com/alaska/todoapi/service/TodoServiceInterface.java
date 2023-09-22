package com.alaska.todoapi.service;

import com.alaska.todoapi.Exception.UserDoesNotExistException;
import com.alaska.todoapi.entity.Todo;

public interface TodoServiceInterface {

    public Todo createTodo(Long id, Todo todo) throws UserDoesNotExistException;
}
