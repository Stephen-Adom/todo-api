package com.alaska.todoapi.service;

import java.util.List;

import com.alaska.todoapi.Exception.TodoAlreadyMarkedAsCompletedException;
import com.alaska.todoapi.Exception.TodoDoesNotExistException;
import com.alaska.todoapi.Exception.UserDoesNotExistException;
import com.alaska.todoapi.entity.Todo;

public interface TodoServiceInterface {

    public Todo createTodo(Long id, Todo todo) throws UserDoesNotExistException;

    public List<Todo> getAllUserTodo(Long id) throws UserDoesNotExistException;

    public Todo updateTodo(Long userId, Long todoId, Todo todo)
            throws UserDoesNotExistException, TodoDoesNotExistException, TodoAlreadyMarkedAsCompletedException;

    public Todo getUserTodoById(Long userId, Long todoId) throws UserDoesNotExistException, TodoDoesNotExistException;

    public void deleteTodoById(Long userId, Long todoId) throws UserDoesNotExistException, TodoDoesNotExistException;

    public Todo markTodoAsComplete(Long userId, Long todoId)
            throws UserDoesNotExistException, TodoDoesNotExistException;

    public List<Todo> getCompletedTodoByUserId(Long userId) throws UserDoesNotExistException;
}
