package com.alaska.todoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alaska.todoapi.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
