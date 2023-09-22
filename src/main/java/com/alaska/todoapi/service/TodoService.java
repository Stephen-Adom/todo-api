package com.alaska.todoapi.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alaska.todoapi.Exception.TodoAlreadyMarkedAsCompletedException;
import com.alaska.todoapi.Exception.TodoDoesNotExistException;
import com.alaska.todoapi.Exception.UserDoesNotExistException;
import com.alaska.todoapi.entity.Todo;
import com.alaska.todoapi.entity.User;
import com.alaska.todoapi.repository.TodoRepository;
import com.alaska.todoapi.repository.UserRepository;

@Service
public class TodoService implements TodoServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public Todo createTodo(Long id, Todo todo) throws UserDoesNotExistException {
        Optional<User> user = this.userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserDoesNotExistException("User with the id " + id + " does not exist");
        }

        new Todo();
        Todo newTodo = Todo.builder().title(todo.getTitle()).description(todo.getDescription())
                .dueDate(todo.getDueDate()).user(user.get()).completed(false).build();

        return this.todoRepository.save(newTodo);
    }

    @Override
    public List<Todo> getAllUserTodo(Long id) throws UserDoesNotExistException {
        if (!this.userRepository.existsById(id)) {
            throw new UserDoesNotExistException("User with the id " + id + " does not exist");
        }

        return this.todoRepository.findAllByUserId(id);
    }

    @Override
    public Todo updateTodo(Long userId, Long todoId, Todo todo)
            throws UserDoesNotExistException, TodoDoesNotExistException, TodoAlreadyMarkedAsCompletedException {
        if (!this.userRepository.existsById(userId)) {
            throw new UserDoesNotExistException("User with the id " + userId + " does not exist");
        }

        Optional<Todo> existTodo = this.todoRepository.findById(todoId);

        if (existTodo.isEmpty()) {
            throw new TodoDoesNotExistException("Todo with id " + todoId + " does not exist");
        }

        if (existTodo.get().getCompleted()) {
            throw new TodoAlreadyMarkedAsCompletedException("Cannot edit todo already  marked as complete");
        }

        Todo existingTodo = existTodo.get();

        existingTodo.setTitle(todo.getTitle());
        existingTodo.setDescription(todo.getDescription());
        existingTodo.setDueDate(todo.getDueDate());

        return this.todoRepository.save(existingTodo);
    }

    @Override
    public Todo getUserTodoById(Long userId, Long todoId) throws UserDoesNotExistException, TodoDoesNotExistException {
        if (!this.userRepository.existsById(userId)) {
            throw new UserDoesNotExistException("User with the id " + userId + " does not exist");
        }

        Optional<Todo> existTodo = this.todoRepository.findById(todoId);

        if (existTodo.isEmpty()) {
            throw new TodoDoesNotExistException("Todo with id " + todoId + " does not exist");
        }

        return existTodo.get();
    }
}
