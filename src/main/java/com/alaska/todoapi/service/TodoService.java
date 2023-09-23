package com.alaska.todoapi.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
        this.checkIfUserExist(id);

        return this.todoRepository.findAllByUserId(id);
    }

    @Override
    public Todo updateTodo(Long userId, Long todoId, Todo todo)
            throws UserDoesNotExistException, TodoDoesNotExistException, TodoAlreadyMarkedAsCompletedException {
        this.checkIfUserExist(userId);

        Todo existTodo = this.checkIfTodoExist(todoId, userId);

        if (existTodo.getCompleted()) {
            throw new TodoAlreadyMarkedAsCompletedException("Cannot edit todo already  marked as complete");
        }

        existTodo.setTitle(todo.getTitle());
        existTodo.setDescription(todo.getDescription());
        existTodo.setDueDate(todo.getDueDate());

        return this.todoRepository.save(existTodo);
    }

    @Override
    public Todo getUserTodoById(Long userId, Long todoId) throws UserDoesNotExistException, TodoDoesNotExistException {
        this.checkIfUserExist(userId);

        Todo existTodo = this.checkIfTodoExist(todoId, userId);
        return existTodo;
    }

    @Override
    public Todo markTodoAsComplete(Long userId, Long todoId)
            throws UserDoesNotExistException, TodoDoesNotExistException {
        this.checkIfUserExist(userId);

        Todo existTodo = this.checkIfTodoExist(todoId, userId);

        existTodo.setCompleted(true);

        return this.todoRepository.save(existTodo);
    }

    @Override
    public void deleteTodoById(Long userId, Long todoId) throws UserDoesNotExistException, TodoDoesNotExistException {
        this.checkIfUserExist(userId);

        Todo existTodo = this.checkIfTodoExist(todoId, userId);

        if (Objects.nonNull(existTodo)) {

            this.todoRepository.deleteById(todoId);
        }

    }

    private void checkIfUserExist(Long userId) throws UserDoesNotExistException {
        if (!this.userRepository.existsById(userId)) {
            throw new UserDoesNotExistException("User with the id " + userId + " does not exist");
        }
    }

    private Todo checkIfTodoExist(Long todoId, Long userId) throws TodoDoesNotExistException {
        Todo existTodo = this.todoRepository.findByIdAndUserId(todoId, userId);

        if (Objects.isNull(existTodo)) {
            throw new TodoDoesNotExistException("User with id " + userId + " does not have todo with id " + todoId);
        }

        return existTodo;
    }
}
