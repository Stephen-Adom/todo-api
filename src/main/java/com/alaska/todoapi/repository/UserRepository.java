package com.alaska.todoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alaska.todoapi.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
