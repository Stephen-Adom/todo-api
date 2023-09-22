package com.alaska.todoapi.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
